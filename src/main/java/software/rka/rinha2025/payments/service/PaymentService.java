package software.rka.rinha2025.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.domain.PaymentStatus;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageStatus;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageType;
import software.rka.rinha2025.payments.persistence.OutboxMessageEntity;
import software.rka.rinha2025.payments.persistence.OutboxMessageRepository;
import software.rka.rinha2025.payments.persistence.PaymentEntity;
import software.rka.rinha2025.payments.persistence.PaymentRepository;

import java.time.Instant;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final OutboxMessageRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public PaymentService(PaymentRepository paymentRepository,
                          OutboxMessageRepository outboxRepository,
                          ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Payment createPayment(CreatePaymentInput input) {
        PaymentEntity paymentEntity = new PaymentEntity(input.correlationId(),
                input.amount(),
                PaymentStatus.PENDING);

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);

        Payment payment =  savedPaymentEntity.toPayment();

        saveProcessPaymentOutbox(payment);

        return payment;
    }

    public void updateWithStatus(Payment payment, PaymentStatus status) {
        paymentRepository.updateWithStatus(payment.getId(), status, Instant.now());
    }

    private void saveProcessPaymentOutbox(Payment payment) {
        try {
            String paymentPayload = objectMapper.writeValueAsString(payment);

            OutboxMessageEntity outboxEntity = new OutboxMessageEntity(
                    OutboxMessageType.PROCESS_PAYMENT,
                    paymentPayload,
                    OutboxMessageStatus.NEW
            );

            outboxRepository.save(outboxEntity);

        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON, payment='{}'", payment);
            throw new RuntimeException("Error processing JSON", e);
        }
    }
}
