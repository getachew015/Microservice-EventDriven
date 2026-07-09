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
public class NewCardDto {

    private Long customerId;
    private String cardType;
    private BigDecimal cardLimit;

}
