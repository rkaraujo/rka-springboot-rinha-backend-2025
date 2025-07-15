package software.rka.rinha2025.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.service.CreatePaymentInput;
import software.rka.rinha2025.payments.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
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
}
