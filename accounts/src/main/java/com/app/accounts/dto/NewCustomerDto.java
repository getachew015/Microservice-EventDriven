package com.app.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCustomerDto extends AddressDto{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String accountType;
    private String balance;

}
