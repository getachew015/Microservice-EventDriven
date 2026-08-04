package com.app.loans.repository;

import com.app.loans.entity.LoanAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanAccountRepository extends JpaRepository<LoanAccountEntity, Long> {

    Optional<List<LoanAccountEntity>> findByCustomerId(Long customerId);

}
