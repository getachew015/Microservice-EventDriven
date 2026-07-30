package com.app.accounts.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String streetAddress;// VARCHAR(20),
    private String city;// VARCHAR(20),
    private String state;// VARCHAR(20),
    private String zipCode;// VARCHAR(20),

}
