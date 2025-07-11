package software.rka.rinha2025.payments.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment {

    private Long id;
    private UUID correlationId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant createdAt;
    private Instant modifiedAt;

    Payment() {
    }

    public Payment(Long id,
                   UUID correlationId,
                   BigDecimal amount,
                   PaymentStatus status,
                   Instant createdAt,
                   Instant modifiedAt) {
        this.id = id;
        this.correlationId = correlationId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }
}
