package software.rka.rinha2025.payments.persistence;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID correlationId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    PaymentEntity() {
    }

    public PaymentEntity(UUID correlationId,
                         BigDecimal amount,
                         PaymentStatus status) {
        this.correlationId = correlationId;
        this.amount = amount;
        this.status = status;
    }

    public Payment toPayment() {
        return new Payment(id, correlationId, amount, status, createdAt, modifiedAt);
    }
}
