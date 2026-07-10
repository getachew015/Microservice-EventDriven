package com.app.cards.controller;

import com.app.cards.dto.CardsDto;
import com.app.cards.dto.ErrorMessageDto;
import com.app.cards.dto.NewCardDto;
import com.app.cards.dto.SuccessMessageDto;
import com.app.cards.entity.CardsEntity;
import com.app.cards.service.CardsService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/cards-api")
@AllArgsConstructor
@Slf4j
public class CardsController {

    private final CardsService cardsService;

    @GetMapping(path = "/v1/cards/{cardAccountId}", name = "Get Card by Card Account ID")
    public ResponseEntity<?> getCardByCardAccountId(@PathVariable Long cardAccountId) {
        // Call the service layer to get the card details
        try {
            CardsDto cardsDto = cardsService.getCardByCardAccountId(cardAccountId);
            return ResponseEntity.ok().body(cardsDto);
        } catch (RuntimeException e) {
            log.error("Error fetching card details for cardAccountId ... " + e);
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @GetMapping(path = "/v1/customer/cards/{customerId}", name = "Get Cards by Customer ID")
    public ResponseEntity<?> getCardsByCustomerId(@PathVariable Long customerId) {
        // Call the service layer to get the card details
        try {
            List<CardsDto> cards = cardsService.getCardsByCustomerId(customerId);
            return ResponseEntity.ok().body(cards);
        } catch (RuntimeException e) {
            log.error("Error fetching card details for cardAccountId ... " + e);
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @PostMapping(path = "/v1/cards/", name = "Get Cards by Customer ID")
    public ResponseEntity<?> openCardsAccount(@RequestBody NewCardDto newCardDto) {
        // Call the service to open card account
        try {
            CardsEntity cards = cardsService.openCardAccount(newCardDto);
            return ResponseEntity.status(CREATED).body(new SuccessMessageDto(CREATED, "Card opened successfully : " + cards.getCardNumber()));
        } catch (RuntimeException e) {
            log.error("Error opening card account ... " + e);
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @PatchMapping(path = "/v1/cards/", name = "Get Cards by Customer ID")
    public ResponseEntity<?> patchCardAccountDetail(@RequestParam Long customerId,
                                                    @RequestParam String lastFourCardNumber,
                                                    @RequestBody JsonPatch cardAccountDetail) {
        //Call service to update card account
        try {
            CardsDto updatedCard = cardsService.updateCardDetails(customerId, lastFourCardNumber, cardAccountDetail);
            log.info("Card detail updated successfully for customerId : {} and last four digits of card number : {} ", updatedCard.getCustomerId(), lastFourCardNumber);
            return ResponseEntity.status(NO_CONTENT).body(new SuccessMessageDto(CREATED, "Card Detailed Updated successfully : "));
        } catch (RuntimeException e) {
            log.error("Error updating card detail ... " + e);
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @DeleteMapping(path = "/v1/cards/", name = "Get Cards by Customer ID")
    public ResponseEntity<?> patchCardAccountDetail(@RequestParam Long customerId,
                                                    @RequestParam String lastFourCardNumber,
                                                    @RequestParam int cvvNumber) {
        //Call service to delete card account
        try {
            CardsDto deletedCard = cardsService.deleteCardAccount(customerId, lastFourCardNumber, cvvNumber);
            log.info("Card detail deleted successfully for customerId : {} and last four digits of card number : {} ", deletedCard.getCustomerId(), lastFourCardNumber);
            return ResponseEntity.status(NO_CONTENT).body(new SuccessMessageDto(NO_CONTENT, "Card Account deleted successfully : "));
        } catch (RuntimeException e) {
            log.error("Error deleting card detail ... " + e);
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

}
