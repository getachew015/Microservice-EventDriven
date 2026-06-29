package com.app.accounts.repository;

import com.app.accounts.entity.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {

    Optional<AccountsEntity> findFirstByCustomerId(Long customerId);
}
