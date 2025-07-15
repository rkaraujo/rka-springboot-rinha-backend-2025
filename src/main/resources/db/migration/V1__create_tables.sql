CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    correlation_id UUID,
    amount BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE payment_success (
    id BIGSERIAL PRIMARY KEY,
    payment_id INTEGER,
    amount BIGINT,
    processor_default BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE payment_fail (
    id BIGSERIAL PRIMARY KEY,
    payment_id INTEGER,
    amount BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_payment_success_created_at_processor_default ON payment_success(created_at, processor_default);
