package com.app.cards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CardsTbl")
public class CardsEntity extends BaseEntity {

    @Id
    @Column(name = "card_account_id")
    private Long cardAccountId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "cvv")
    private int cvv;
    @Column(name = "debit_balance")
    private BigDecimal debitBalance;
    @Column(name = "credit_balance")
    private BigDecimal creditBalance;
    @Column(name = "card_limit")
    private BigDecimal cardLimit;
    @Column(name = "is_active")
    private Boolean activeStatus;
}
