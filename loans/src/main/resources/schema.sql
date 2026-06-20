-- Create optional custom schema if not using the default PUBLIC schema
CREATE SCHEMA IF NOT EXISTS comm_banking_system;
SET SCHEMA comm_banking_system;

-- 4. Loans Table
CREATE TABLE Loans (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    loan_type VARCHAR(50) NOT NULL, -- e.g., MORTGAGE, PERSONAL, AUTO
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL, -- e.g., 5.50 for 5.5%
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    CONSTRAINT pk_loans PRIMARY KEY (loan_id)
);