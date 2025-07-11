package software.rka.rinha2025.payments.infrastructure.outbox.strategy;

import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageType;
import software.rka.rinha2025.payments.persistence.OutboxMessageEntity;

public interface OutboxProcessStrategy {

    void process(OutboxMessageEntity outboxMessage);

    OutboxMessageType getType();

}
