package com.app.accounts.controller;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.dto.ErrorMessageDto;
import com.app.accounts.dto.SuccessMessageDto;
import com.app.accounts.service.CustomerAccountsService;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/customer-accounts", name = "Customer Accounts API")
@AllArgsConstructor
public class CustomerAccountsController {

    private final CustomerAccountsService customerAccountsService;

    @GetMapping(path = "/v1/users/{id}", name = "Get Customer by ID")
    public ResponseEntity<CustomerAccountDto> getCustomerById(@PathVariable Long id) {
        // Implement logic to retrieve customer by ID
        return ResponseEntity.ok().body(customerAccountsService.findCustomerById(id));
    }

    @PostMapping(path = "/v1/users", name = "Create Customer Account")
    public ResponseEntity<SuccessMessageDto> createCustomerAccount(@RequestBody CustomerDto customer) {
        // Create a new customer account
        customerAccountsService.addNewCustomer(customer);
        return ResponseEntity.ok().body(new SuccessMessageDto(
                CREATED, "Customer account created successfully"));
    }

    @PatchMapping(path = "/v1/accounts/{accountId}", consumes = "application/json-patch+json", name = "Patch Customer by ID")
    public ResponseEntity<?> patchCustomerById(@PathVariable Long accountId, @RequestBody @Schema(hidden = true) JsonPatch patchCustomerDtl) {
        // Implement logic to retrieve customer by ID
        try {
            customerAccountsService.patchCustomerDetail(accountId, patchCustomerDtl);
            return ResponseEntity.ok().body(new SuccessMessageDto(HttpStatus.OK, "Customer account updated successfully"));
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @DeleteMapping(path = "/v1/accounts/{accountId}", name = "Delete Customer Account by ID")
    public ResponseEntity<?> deleteCustomerAccountById(@PathVariable Long accountId) {
        // Implement logic to retrieve customer by ID
        try {
            customerAccountsService.deleteCustomerAccount(accountId);
            return ResponseEntity.status(CREATED).body(new SuccessMessageDto(HttpStatus.OK, "Customer account deleted successfully"));
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.internalServerError()
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

}
