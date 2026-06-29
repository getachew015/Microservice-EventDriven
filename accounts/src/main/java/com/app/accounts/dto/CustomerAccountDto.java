package com.app.accounts.dto;

import lombok.Data;

@Data
public class CustomerAccountDto {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String accountNumber;
    private String accountType;
    private String balance;

}
