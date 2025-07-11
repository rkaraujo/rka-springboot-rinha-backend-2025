package software.rka.rinha2025.payments.controller;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(Long id, UUID correlationId, BigDecimal amount) {
}
