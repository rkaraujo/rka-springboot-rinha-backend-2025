package software.rka.rinha2025.payments.persistence;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageStatus;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageType;

import java.time.Instant;

@Entity
@Table(name = "outbox")
public class OutboxMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OutboxMessageType type;

    private String payload; // JSON payload

    @Enumerated(EnumType.STRING)
    private OutboxMessageStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    OutboxMessageEntity() {
    }

    public OutboxMessageEntity(OutboxMessageType type,
                               String payload,
                               OutboxMessageStatus status) {
        this.type = type;
        this.payload = payload;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public OutboxMessageType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}
