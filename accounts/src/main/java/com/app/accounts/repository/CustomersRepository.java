package com.app.accounts.repository;

import com.app.accounts.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomersRepository extends JpaRepository<CustomersEntity, Long> {
}
