package com.app.cards.repository;

import com.app.cards.entity.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends JpaRepository<CardsEntity, Long> {

    Optional<List<CardsEntity>> findByCustomerId(Long customerId);

    Optional<CardsEntity> findByCardNumber(String cardNumber);

    @Query("Select C FROM CardsEntity C WHERE C.customerId = :customerId AND right(C.cardNumber, 4) = :cardNumber")
    Optional<CardsEntity> findByCustomerIdAndLastFourDigitCardNumber(Long customerId, String cardNumber);
}
