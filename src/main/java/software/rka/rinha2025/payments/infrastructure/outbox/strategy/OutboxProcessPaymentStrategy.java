package software.rka.rinha2025.payments.infrastructure.outbox.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.domain.PaymentStatus;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageType;
import software.rka.rinha2025.payments.infrastructure.payment.PaymentProcessorClient;
import software.rka.rinha2025.payments.infrastructure.payment.PaymentProcessorRequest;
import software.rka.rinha2025.payments.persistence.OutboxMessageEntity;
import software.rka.rinha2025.payments.service.PaymentService;

import java.time.Instant;

@Component
public class OutboxProcessPaymentStrategy implements OutboxProcessStrategy {

    private static final Logger logger = LoggerFactory.getLogger(OutboxProcessPaymentStrategy.class);

    private final PaymentProcessorClient paymentProcessorClient;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    public OutboxProcessPaymentStrategy(PaymentProcessorClient paymentProcessorClient,
                                        PaymentService paymentService,
                                        ObjectMapper objectMapper) {
        this.paymentProcessorClient = paymentProcessorClient;
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(OutboxMessageEntity outboxMessage) {
        logger.info("Start processing payment outbox message id={}", outboxMessage.getId());

        try {
            Payment payment = objectMapper.readValue(outboxMessage.getPayload(), Payment.class);

            Instant requestedAt = Instant.now();
            PaymentProcessorRequest request = new PaymentProcessorRequest(payment.getCorrelationId(), payment.getAmount(), requestedAt);

            paymentProcessorClient.makePayment(request);

            paymentService.updateWithStatus(payment, PaymentStatus.COMPLETED);

        } catch (JsonProcessingException e) {
            logger.error("Error deserializing payment, outboxMessageId={}", outboxMessage.getId());
            throw new RuntimeException("Error deserializing payment, outboxMessageId=" + outboxMessage.getId(), e);
        }

        logger.info("Finished processing payment outbox message id={}", outboxMessage.getId());
    }

    @Override
    public OutboxMessageType getType() {
        return OutboxMessageType.PROCESS_PAYMENT;
    }
}
