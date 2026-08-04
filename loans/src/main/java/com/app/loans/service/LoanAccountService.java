package com.app.loans.service;

import com.app.loans.dto.LoanAccountDto;
import com.app.loans.dto.NewLoanDto;
import com.app.loans.entity.LoanAccountEntity;
import com.app.loans.mapper.LoanAccountMapper;
import com.app.loans.repository.LoanAccountRepository;
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
}
