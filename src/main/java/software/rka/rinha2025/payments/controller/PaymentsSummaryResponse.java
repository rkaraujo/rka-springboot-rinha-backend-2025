package software.rka.rinha2025.payments.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.rka.rinha2025.payments.domain.PaymentSummary;

public record PaymentsSummaryResponse(@JsonProperty("default") PaymentSummaryResponse defaultPaymentSummaryResponse,
                                      @JsonProperty("fallback") PaymentSummaryResponse fallbackPaymentSummaryResponse) {

    public PaymentsSummaryResponse(PaymentSummary defaultPaymentSummary, PaymentSummary fallbackPaymentSummary) {
        this(
                defaultPaymentSummary != null ? new PaymentSummaryResponse(defaultPaymentSummary) : null,
                fallbackPaymentSummary != null ? new PaymentSummaryResponse(fallbackPaymentSummary) : null
        );
    }
}
