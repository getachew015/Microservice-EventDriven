package com.app.accounts.controller;

import com.app.accounts.dto.*;
import com.app.accounts.service.CustomerAccountsService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/customer-accounts", name = "Customer Accounts API")
@AllArgsConstructor
public class CustomerAccountsController {

    private final CustomerAccountsService customerAccountsService;

    @GetMapping(path = "/v1/customers/accounts/{id}", name = "Get Customer by ID")
    public ResponseEntity<?> getCustomerAcctDetailById(@PathVariable Long id) {
        // Find customer account details by ID
        try {
            return ResponseEntity.ok().body(customerAccountsService.findCustomerAcctDetailById(id));
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @GetMapping(path = "/v1/customers/{id}", name = "Get Customer by ID")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        // Find customer profile by ID
        try {
            return ResponseEntity.ok().body(customerAccountsService.findCustomerProfileById(id));
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @PostMapping(path = "/v1/customers", name = "Create Customer Account")
    public ResponseEntity<SuccessMessageDto> createCustomerAccount(@RequestBody NewCustomerDto customer) {
        // Create a new customer account
        customerAccountsService.addNewCustomer(customer);
        return ResponseEntity.status(CREATED).body(new SuccessMessageDto(
                CREATED, "Customer account created successfully"));
    }

    @PostMapping(path = "/v1/customer/profiles", name = "Search Customer Profiles")
    public ResponseEntity<?> searchCustomerProfiles(@RequestBody SearchCustomerDto searchCustomerDto) {
        // Search customer profile by attributes
        try {
            return ResponseEntity.ok()
                    .body(customerAccountsService.getAllCustomers(searchCustomerDto));
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ErrorMessageDto(INTERNAL_SERVER_ERROR, e.getMessage(), location));
        }
    }

    @PatchMapping(path = "/v1/accounts/{accountId}", consumes = "application/json-patch+json", name = "Patch Customer by ID")
    public ResponseEntity<?> patchCustomerById(@PathVariable Long accountId, @RequestBody JsonPatch patchCustomerDtl) {
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
