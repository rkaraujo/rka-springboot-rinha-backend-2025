package software.rka.rinha2025.payments.controller;

import software.rka.rinha2025.payments.domain.PaymentSummary;

import java.math.BigDecimal;

public record PaymentSummaryResponse(int totalRequests, BigDecimal totalAmount) {

    public PaymentSummaryResponse(PaymentSummary paymentSummary) {
        this(paymentSummary.totalRequests(), paymentSummary.totalAmount());
    }
}
