package com.app.loans.controller;

import com.app.loans.dto.ErrorMessageDto;
import com.app.loans.dto.LoanAccountDto;
import com.app.loans.dto.NewLoanDto;
import com.app.loans.dto.SuccessMessageDto;
import com.app.loans.entity.LoanAccountEntity;
import com.app.loans.service.LoanAccountService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/loan-api")
@AllArgsConstructor
@Slf4j
public class LoanAccountController {

    private final LoanAccountService loanAccountService;

    @PostMapping(path = "/v1/loans", name = "Create a loan Account")
    public ResponseEntity<SuccessMessageDto> createCustomerLoanAccount(@RequestBody NewLoanDto newLoanDto) {
        // Create a new customer account
        LoanAccountEntity createdLoanAcct = loanAccountService.createLoanAccount(newLoanDto);
        return ResponseEntity.status(CREATED).body(new SuccessMessageDto(
                CREATED, "Loan account created successfully" + createdLoanAcct.getLoanId()));
    }

    @PatchMapping(path = "/v1/loans", name = "Update a loan Account")
    public ResponseEntity<?> patchCustomerLoanAccount(@RequestParam Long loanId,
                                                      @RequestBody JsonPatch loanAccountDetail) {
        // Create a new customer account
        try {
            LoanAccountDto loanAccountDto = loanAccountService.updateLoanAccount(loanId, loanAccountDetail);
            if (loanAccountDto == null)
                return ResponseEntity.status(NOT_FOUND).body(
                        new SuccessMessageDto(NOT_FOUND, "Loan accounts not found for loanId: " + loanId));
            else
                return ResponseEntity.status(OK).body(loanAccountDto);
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            log.error("Error occurred while updating loan account with loanId: {}", loanId, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorMessageDto(
                    INTERNAL_SERVER_ERROR,
                    "An error occurred while updating the loan account.", location));
        }

    }

    @GetMapping(path = "/v1/customer/loans", name = "Find a customer loan Accounts")
    public ResponseEntity<?> findCustomerLoanAccounts(@RequestParam long customerId) {
        // Find customer loan account
        try {
            List<LoanAccountDto> loanAccounts = loanAccountService.getLoanAccounts(customerId);
            if (loanAccounts == null || loanAccounts.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(
                        new SuccessMessageDto(NOT_FOUND, "Loan accounts not found for customerId: " + customerId));
            else
                return ResponseEntity.status(OK).body(loanAccounts);
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            log.error("Error occurred while retrieving loan account for customerId: {}", customerId, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorMessageDto(
                    INTERNAL_SERVER_ERROR,
                    "An error occurred while retrieving the loan accounts.", location));
        }
    }

    @GetMapping(path = "/v1/customer/loan", name = "Create a customer loan Account")
    public ResponseEntity<?> findCustomerLoanAccount(@RequestParam long loanId) {
        // Find customer loan account
        try {
            LoanAccountDto loanAccountDto = loanAccountService.getLoanAccount(loanId);
            if (loanAccountDto == null)
                return ResponseEntity.status(NOT_FOUND).body(
                        new SuccessMessageDto(NOT_FOUND, "Loan account not found for loanId: " + loanId));
            else
                return ResponseEntity.status(OK).body(loanAccountDto);
        } catch (Exception e) {
            String location = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
            log.error("Error occurred while retrieving loan account for loanId: {}", loanId, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorMessageDto(
                    INTERNAL_SERVER_ERROR,
                    "An error occurred while retrieving the loan account.", location));
        }
    }

}
