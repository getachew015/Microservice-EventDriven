-- Create optional custom schema if not using the default PUBLIC schema
CREATE SCHEMA IF NOT EXISTS comm_banking_system;
SET SCHEMA comm_banking_system;

-- 4. Loans Table
CREATE TABLE LoansTbl (
    loan_id BIGINT,
    customer_id BIGINT NOT NULL,
    loan_type VARCHAR(50) NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) ,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_loans PRIMARY KEY (loan_id),
    conSTRAINT chk_loan_type CHECK (loan_type IN ('MORTGAGE', 'PERSONAL', 'AUTO', 'BUSINESS'))
);