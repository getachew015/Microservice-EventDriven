-- Create optional custom schema if not using the default PUBLIC schema
CREATE SCHEMA IF NOT EXISTS comm_banking_system;
SET SCHEMA comm_banking_system;

-- Drop tables if they exist to allow clean local testing/re-runs
DROP TABLE IF EXISTS CardsTbl;

-- 3. Cards Table
CREATE TABLE CardsTbl (
    card_account_id BIGINT,
    customer_id BIGINT NOT NULL,
    card_number VARCHAR(19) NOT NULL,
    card_type VARCHAR(20) NOT NULL DEFAULT 'DEBIT',
    expiration_date DATE NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    debit_balance DECIMAL(15, 2) DEFAULT 0.00,
    credit_balance DECIMAL(15, 2) DEFAULT 0.00,
    card_limit DECIMAL(15, 2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_by VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) ,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_cards PRIMARY KEY (card_account_id),
    CONSTRAINT uq_card_number UNIQUE (card_number),
    CONSTRAINT chk_card_type CHECK (card_type IN ('DEBIT', 'CREDIT', 'PREPAID', 'CHARGE'))
);


-- Indexes for Query Optimization
CREATE INDEX idx_card_number ON CardsTbl(card_number);
