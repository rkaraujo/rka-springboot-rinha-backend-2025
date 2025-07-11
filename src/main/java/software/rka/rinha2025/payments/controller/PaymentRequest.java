package software.rka.rinha2025.payments.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        @NotNull UUID correlationId,
        @NotNull @Positive BigDecimal amount) {
}
