package com.app.accounts.controller;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.dto.SuccessMessageDto;
import com.app.accounts.service.CustomerAccountsService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/customers-api", name = "Customers API")
@AllArgsConstructor
public class CustomersController {

    private final CustomerAccountsService customerAccountsService;

    @GetMapping(path = "/v1/users/{id}")
    public ResponseEntity<CustomerAccountDto> getCustomerById(@PathVariable Long id) {
        // Implement logic to retrieve customer by ID
        return ResponseEntity.ok().body(customerAccountsService.findCustomerById(id));
    }

    @PostMapping(path = "/v1/users")
    public ResponseEntity<SuccessMessageDto> createCustomerAccount(@RequestBody CustomerDto customer) {
        // Create a new customer account
        customerAccountsService.addNewCustomer(customer);
        return ResponseEntity.ok().body(new SuccessMessageDto(
                CREATED, "Customer account created successfully"));
    }

    @PatchMapping(path = "/v1/accounts/{accountId}", consumes = "application/json-patch+json", name = "Patch Customer by ID")
    public ResponseEntity<SuccessMessageDto> patchCustomerById(@PathVariable Long accountId, @RequestBody JsonPatch patchCustomerDtl) {
        // Implement logic to retrieve customer by ID
        try {
            customerAccountsService.patchCustomerDetail(accountId, patchCustomerDtl);
            return ResponseEntity.ok().body(new SuccessMessageDto(HttpStatus.OK, "Customer account updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
