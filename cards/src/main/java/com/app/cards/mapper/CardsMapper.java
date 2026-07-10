package com.app.cards.mapper;

import com.app.cards.dto.CardsDto;
import com.app.cards.dto.NewCardDto;
import com.app.cards.dto.PatchCardsDto;
import com.app.cards.entity.CardsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = IGNORE)
public interface CardsMapper {

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "cardNumber", source = "cardNumber")
    CardsDto toCardsDto(CardsEntity cardsEntity);

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", expression = "java(\"SYSTEM\")")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"USER\")")
    CardsEntity toCardsEntity(CardsDto cardsDto);

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "cardLimit", source = "cardLimit")
    @Mapping(target = "cardType", source = "cardType")
    @Mapping(target = "activeStatus", expression = "java(true)")
    @Mapping(target = "deletedStatus", expression = "java(false)")
    CardsDto toCardsDtoFromNewCardDto(NewCardDto newCardDto);

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "cardNumber", source = "cardNumber")
    PatchCardsDto toPatchCardsDto(CardsEntity cardsEntity);

    @Mapping(target = "cardAccountId", source = "existingEntity.cardAccountId")
    @Mapping(target = "cardType", source = "existingEntity.cardType")
    @Mapping(target = "expirationDate", source = "existingEntity.expirationDate")
    @Mapping(target = "cvv", source = "existingEntity.cvv")
    @Mapping(target = "customerId", source = "existingEntity.customerId")
    @Mapping(target = "cardNumber", source = "existingEntity.cardNumber")
    @Mapping(target = "debitBalance", expression = "java(patchCardsDto.getDebitBalance() != null ? patchCardsDto.getDebitBalance() : existingEntity.getDebitBalance())")
    @Mapping(target = "creditBalance", expression = "java(patchCardsDto.getCreditBalance() != null ? patchCardsDto.getCreditBalance() : existingEntity.getCreditBalance())")
    @Mapping(target = "cardLimit", expression = "java(patchCardsDto.getCardLimit() != null ? patchCardsDto.getCardLimit() : existingEntity.getCardLimit())")
    @Mapping(target = "activeStatus", expression = "java(patchCardsDto.getActiveStatus() != null ? patchCardsDto.getActiveStatus() : existingEntity.getActiveStatus())")
    @Mapping(target = "deletedStatus", expression = "java(patchCardsDto.getDeletedStatus() != null ? patchCardsDto.getDeletedStatus() : existingEntity.getDeletedStatus())")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"USER\")")
    CardsEntity toCardsEntityFromPatchCardsDto(PatchCardsDto patchCardsDto, CardsEntity existingEntity);
}
