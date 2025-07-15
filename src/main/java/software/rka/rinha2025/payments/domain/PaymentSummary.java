package software.rka.rinha2025.payments.domain;

import java.math.BigDecimal;

public record PaymentSummary(int totalRequests, BigDecimal totalAmount) {
}
