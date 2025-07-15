package software.rka.rinha2025.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.domain.PaymentSummary;
import software.rka.rinha2025.payments.service.CreatePaymentInput;
import software.rka.rinha2025.payments.service.PaymentService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RestController
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        if (paymentRequest.correlationId() == null) {
            throw new RuntimeException("No correlationId received");
        }
        if (paymentRequest.amount() == null || paymentRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        Payment payment = paymentService.createPayment(new CreatePaymentInput(paymentRequest.correlationId(), paymentRequest.amount()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new PaymentResponse(payment.getId(), paymentRequest.correlationId(), payment.getAmount()));
    }

    @GetMapping("/payments-summary")
    public PaymentsSummaryResponse getPaymentsSummary(@RequestParam Instant from, @RequestParam Instant to) {
        Map<String, PaymentSummary> paymentSummaryByPaymentProcessorLabel = paymentService.getPaymentsSummary(from, to);
        PaymentSummary defaultPaymentSummary = paymentSummaryByPaymentProcessorLabel.get("default");
        PaymentSummary fallbackPaymentSummary = paymentSummaryByPaymentProcessorLabel.get("fallback");
        return new PaymentsSummaryResponse(defaultPaymentSummary, fallbackPaymentSummary);
    }
}
