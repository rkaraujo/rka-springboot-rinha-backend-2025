package software.rka.rinha2025.payments.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment {

    private Long id;
    private UUID correlationId;
    private BigDecimal amount;
    private Instant createdAt;

    Payment() {
    }

    public Payment(Long id,
                   UUID correlationId,
                   BigDecimal amount,
                   Instant createdAt) {
        this.id = id;
        this.correlationId = correlationId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Payment(UUID correlationId,
                   BigDecimal amount,
                   Instant createdAt) {
        this.correlationId = correlationId;
        this.amount = amount;
        this.createdAt = createdAt;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}
