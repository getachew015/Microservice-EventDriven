package com.app.accounts.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto {

    private HttpStatus errorCode;
    private String errorMessage;
    private String apiPath;
}
