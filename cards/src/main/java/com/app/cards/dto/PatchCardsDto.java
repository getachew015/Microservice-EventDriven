package com.app.cards.dto;

import com.app.cards.config.CustomSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchCardsDto {

    private Long customerId;
    @JsonSerialize(using = CustomSerializer.class)
    private String cardNumber;
    private BigDecimal debitBalance;
    private BigDecimal creditBalance;
    private BigDecimal cardLimit;
    private Boolean activeStatus;
    private Boolean deletedStatus;
}
