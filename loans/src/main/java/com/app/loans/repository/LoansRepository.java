package com.app.loans.repository;

import com.app.loans.entity.LoansEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<LoansEntity, Long> {

}
