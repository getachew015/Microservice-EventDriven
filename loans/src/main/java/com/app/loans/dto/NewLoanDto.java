package com.app.loans.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewLoanDto {

    private Long customerId;
    private String loanType;
    private String principalAmount;

}
