package com.app.accounts.repository;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.entity.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {

    Optional<AccountsEntity> findFirstByCustomerId(Long customerId);

    @Query("SELECT c.customerId, c.firstName, c.lastName, c.email, c.phoneNumber, a.accountNumber, a.accountType, a.balance " +
            "FROM CustomersEntity c left join AccountsEntity a on a.customerId = c.customerId " +
            "WHERE c.customerId = :customerId ")
    Optional<List<CustomerAccountDto>> findAllAccountsByCustomerId(Long customerId);
}
