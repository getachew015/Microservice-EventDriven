-- Create optional custom schema if not using the default PUBLIC schema
CREATE SCHEMA IF NOT EXISTS comm_banking_system;
SET SCHEMA comm_banking_system;

-- Drop tables if they exist to allow clean local testing/re-runs
DROP TABLE IF EXISTS Cards;

-- 3. Cards Table
CREATE TABLE Cards (
    card_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    card_type VARCHAR(20) NOT NULL, -- e.g., DEBIT, CREDIT
    expiration_date DATE NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_cards PRIMARY KEY (card_id)
);


