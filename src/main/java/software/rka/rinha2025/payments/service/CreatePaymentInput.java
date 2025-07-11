package software.rka.rinha2025.payments.service;


import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentInput(UUID correlationId, BigDecimal amount) {
}
