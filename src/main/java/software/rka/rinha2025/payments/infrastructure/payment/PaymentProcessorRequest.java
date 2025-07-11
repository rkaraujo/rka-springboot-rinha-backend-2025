package software.rka.rinha2025.payments.infrastructure.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentProcessorRequest(UUID correlationId, BigDecimal amount, Instant requestedAt) {
}
