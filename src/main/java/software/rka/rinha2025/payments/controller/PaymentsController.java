package software.rka.rinha2025.payments.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.service.CreatePaymentInput;
import software.rka.rinha2025.payments.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.createPayment(new CreatePaymentInput(paymentRequest.correlationId(), paymentRequest.amount()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new PaymentResponse(payment.getId(), paymentRequest.correlationId(), payment.getAmount()));
    }
}
