package com.app.accounts.dto;

import lombok.Data;

@Data
public class PatchCustomerAccountDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String balance;
    private String accountStatus;
    private String customerStatus;
}
