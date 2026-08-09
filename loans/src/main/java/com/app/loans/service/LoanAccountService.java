package com.app.loans.service;

import com.app.loans.dto.LoanAccountDto;
import com.app.loans.dto.NewLoanDto;
import com.app.loans.dto.PatchLoanAccountDto;
import com.app.loans.entity.LoanAccountEntity;
import com.app.loans.mapper.LoanAccountMapper;
import com.app.loans.repository.LoanAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LoanAccountService {

    private final LoanAccountRepository loanAccountRepository;
    private final LoanAccountMapper loanAccountMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoanAccountEntity createLoanAccount(NewLoanDto newLoanDto) {
        LoanAccountEntity loanAccountEntity = loanAccountMapper.toLoansEntity(newLoanDto);
        Long loanId = ThreadLocalRandom.current().nextLong(10_000, 100_000); // Generates a random number between 10,000 and 99,999
        loanAccountEntity.setLoanId(loanId);
        return loanAccountRepository.save(loanAccountEntity);
    }

    public List<LoanAccountDto> getLoanAccounts(Long customerId) {
        List<LoanAccountEntity> loanAccounts = loanAccountRepository.findByCustomerId(customerId).orElse(null);
        assert loanAccounts != null;
        return loanAccounts.stream()
                .map(loanAccountMapper::toLoansAccountDto)
                .toList();
    }

    public LoanAccountDto getLoanAccount(Long loanId) {
        return loanAccountRepository.findById(loanId)
                .map(loanAccountMapper::toLoansAccountDto)
                .orElse(null);
    }

    public LoanAccountDto updateLoanAccount(Long loanId, JsonPatch patchLoanAccountDetail) {
        objectMapper.registerModule(new JavaTimeModule());
        LoanAccountEntity loanAccount = loanAccountRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Update Not possible, Loan account not found for loanId: " + loanId));
        if (!loanAccount.getActiveStatus() || loanAccount.getDeletedStatus()) {
            throw new RuntimeException("Update Not possible, Loan account is already closed for loanId: " + loanId);
        }
        // Perform the update logic here (e.g., update fields, save to repository)
        try {
            PatchLoanAccountDto patchLoanAccountDto = loanAccountMapper.toPatchLoanAccountDto(loanAccount);
            JsonNode trgtPatchLoanAccountDetail = objectMapper.convertValue(patchLoanAccountDto, JsonNode.class);
            JsonNode patchedCardDetail = null;
            patchedCardDetail = patchLoanAccountDetail.apply(trgtPatchLoanAccountDetail);
            PatchLoanAccountDto patchedLoanAccountDto = objectMapper.treeToValue(patchedCardDetail, PatchLoanAccountDto.class);
            LoanAccountEntity updatedLoanAccountEntity = loanAccountMapper.toLoanAccountEntityFromPatchLoanAccountDto(patchedLoanAccountDto, loanAccount);
            loanAccountRepository.save(updatedLoanAccountEntity);
            return loanAccountMapper.toLoansAccountDto(updatedLoanAccountEntity);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
