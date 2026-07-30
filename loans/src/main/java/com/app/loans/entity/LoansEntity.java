package com.app.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "LoansTbl")
public class LoansEntity extends BaseEntity {
    /*
    loan_id BIGINT,
    customer_id BIGINT NOT NULL,
    loan_type VARCHAR(50) NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    * */
    @Id
    @Column(name = "loan_id")
    private Long loanId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "loan_type")
    private String loanType;
    @Column(name = "principal_amount")
    private BigDecimal principalAmount;
    @Column(name = "interest_rate")
    private float interestRate;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "is_active")
    private Boolean activeStatus;
    @Column(name = "is_deleted")
    private Boolean deletedStatus;
}
