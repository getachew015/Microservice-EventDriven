package com.app.loans.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoanAccountDto {

    private Long loanId;
    private Long customerId;
    private String loanType;
    private BigDecimal principalAmount;
    private float interestRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean activeStatus;
    private Boolean deletedStatus;

}
