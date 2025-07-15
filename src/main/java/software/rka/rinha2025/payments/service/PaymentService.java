package software.rka.rinha2025.payments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.infrastructure.payment.PaymentProcessorClient;
import software.rka.rinha2025.payments.infrastructure.payment.PaymentProcessorRequest;
import software.rka.rinha2025.payments.infrastructure.payment.PaymentProcessorResponse;
import software.rka.rinha2025.payments.persistence.PaymentRepository;

import java.time.Instant;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentProcessorClient paymentProcessorClient;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentProcessorClient paymentProcessorClient) {
        this.paymentRepository = paymentRepository;
        this.paymentProcessorClient = paymentProcessorClient;
    }

    public Payment createPayment(CreatePaymentInput input) {
        Payment payment = new Payment(input.correlationId(),
                input.amount(),
                Instant.now());

        Payment savedPayment = paymentRepository.insert(payment);

        callPaymentProcessor(savedPayment);

        return payment;
    }

    private void callPaymentProcessor(Payment payment) {
        try {
            PaymentProcessorResponse paymentProcessorResponse = paymentProcessorClient.makePayment(new PaymentProcessorRequest(payment.getCorrelationId(),
                    payment.getAmount(),
                    payment.getCreatedAt()));

            paymentRepository.insertPaymentSuccess(payment, paymentProcessorResponse.isPaymentProcessorDefault());
        } catch (RuntimeException e) {
            logger.error("Error calling payment processor, payment={}", payment, e);
            paymentRepository.insertPaymentFail(payment);
            throw e;
        }
    }
}
