package com.app.accounts.repository;

import com.app.accounts.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface CustomersRepository extends JpaRepository<CustomersEntity, Long>, JpaSpecificationExecutor<CustomersEntity> {

    Optional<CustomersEntity> findByCustomerId(Long customerId);
}
