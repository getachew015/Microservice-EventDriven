package com.app.cards.service;

import com.app.cards.dto.CardsDto;
import com.app.cards.dto.NewCardDto;
import com.app.cards.dto.PatchCardsDto;
import com.app.cards.entity.CardsEntity;
import com.app.cards.mapper.CardsMapper;
import com.app.cards.repository.CardsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class CardsService {

    private final CardsRepository cardsRepository;
    private final CardsMapper cardsMapper;
    private final ObjectMapper objectMapper;

    public CardsDto getCardByCardAccountId(Long cardAccountId) {

        CardsEntity cardsEntity = cardsRepository.findById(cardAccountId)
                .orElseThrow(() -> new RuntimeException("Card not found for cardAccountId: " + cardAccountId));
        // Convert CardsEntity to CardsDto and return
        return cardsMapper.toCardsDto(cardsEntity);
    }

    public List<CardsDto> getCardsByCustomerId(Long customerId) {

        List<CardsEntity> cards = cardsRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Card not found for customer with Id : " + customerId));
        // Convert CardsEntity to CardsDto and return
        return cards.stream()
                .map(cardsMapper::toCardsDto)
                .toList();
    }

    public CardsEntity getCardByCustomerIdAndLastFourDigitCardNumber(Long customerId, String cardNumber) {
        return cardsRepository.findByCustomerIdAndLastFourDigitCardNumber(customerId, cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found for customer with Id : " + customerId + " and last four digits of card number: " + cardNumber));
    }

    public CardsEntity getCardByCardNumber(String cardNumber) {
        return cardsRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found for card number: " + cardNumber));
    }

    public CardsEntity openCardAccount(NewCardDto newCardDto) {
        // Convert CardsDto to CardsEntity
        CardsEntity cardsEntity = cardsMapper.toCardsEntity(populateCardDetails(newCardDto));
        cardsEntity.setCardAccountId(ThreadLocalRandom.current().nextLong(10000, 100000));
        cardsRepository.save(cardsEntity);
        // Convert CardsEntity to CardsDto and return
        return cardsEntity;
    }

    public CardsDto updateCardDetails(Long customerId, String lastFourCardNum, JsonPatch patchCardDetail) {
        CardsEntity cardsEntity = getCardByCustomerIdAndLastFourDigitCardNumber(customerId, lastFourCardNum);
        PatchCardsDto patchCardsDto = cardsMapper.toPatchCardsDto(cardsEntity);
        JsonNode trgtPatchCardDetail = objectMapper.convertValue(patchCardsDto, JsonNode.class);
        try {
            JsonNode patchedCardDetail = patchCardDetail.apply(trgtPatchCardDetail);
            PatchCardsDto updatedPatchCardsDto = objectMapper.treeToValue(patchedCardDetail, PatchCardsDto.class);
            CardsEntity updatedCardsEntity = cardsMapper.toCardsEntityFromPatchCardsDto(updatedPatchCardsDto, cardsEntity);
            cardsRepository.save(updatedCardsEntity);
            return cardsMapper.toCardsDto(updatedCardsEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply patch to card details: " + e.getMessage(), e);
        }
    }
    private CardsDto populateCardDetails(NewCardDto newCardDto) {
        CardsDto cardsDto = cardsMapper.toCardsDtoFromNewCardDto(newCardDto);
        // Generate a random card number
        cardsDto.setCardNumber(getCardNumber());
        cardsDto.setCvv(ThreadLocalRandom.current().nextInt(100, 1000)); // Generate a random 3-digit CVV
        cardsDto.setCreditBalance(newCardDto.getCardLimit()); // Set credit balance to card limit
        cardsDto.setDebitBalance(new BigDecimal("0.00")); // Set debit balance to 0.00
        cardsDto.setExpirationDate(LocalDateTime.now().plusYears(3)); // Set expiration date to 3 years from now
        return cardsDto;
    }

    private String getCardNumber() {
        long lowerBound = 4_000_000_000_000_000L;
        long upperBound = 6_999_999_999_999_999L;
        // Generate a random 16-digit long
        long raw16Digits = ThreadLocalRandom.current().nextLong(lowerBound, upperBound + 1);
        // Format the raw long into 4-digit blocks separated by spaces
        return String.format("%016d", raw16Digits)
                .replaceAll(".{4}(?=.)", "$0-") // Insert a dash after every 4 digits
                .trim();
    }
}
