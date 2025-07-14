package software.rka.rinha2025.payments.infrastructure.outbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.rka.rinha2025.payments.infrastructure.outbox.strategy.OutboxProcessStrategy;
import software.rka.rinha2025.payments.persistence.OutboxMessageEntity;
import software.rka.rinha2025.payments.persistence.OutboxMessageRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OutboxProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OutboxProcessor.class);

    private static final int MAX_OUTBOX_QUERY_PAGE_SIZE = 500;

    private final OutboxMessageRepository outboxRepository;
    private final Map<OutboxMessageType, OutboxProcessStrategy>  outboxProcessStrategiesMap;

    public OutboxProcessor(OutboxMessageRepository outboxRepository, List<OutboxProcessStrategy> outboxProcessStrategies) {
        this.outboxRepository = outboxRepository;
        this.outboxProcessStrategiesMap = outboxProcessStrategies.stream()
                .collect(Collectors.toMap(OutboxProcessStrategy::getType, Function.identity()));
    }

    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        logger.info("Start processing outbox");

        List<OutboxMessageEntity> pageOutboxMessages;

        do {
            pageOutboxMessages = outboxRepository.findNewOutboxMessages(MAX_OUTBOX_QUERY_PAGE_SIZE);

            logger.info("Found {} messages to process", pageOutboxMessages.size());
            for (OutboxMessageEntity outboxMessage : pageOutboxMessages) {
                processMessage(outboxMessage);
            }

        } while (!pageOutboxMessages.isEmpty());

        logger.info("Finished processing outbox");
    }

    private void processMessage(OutboxMessageEntity outboxMessage) {
        try {
            OutboxProcessStrategy outboxProcessStrategy = outboxProcessStrategiesMap.get(outboxMessage.getType());
            if (outboxProcessStrategy != null) {
                outboxProcessStrategy.process(outboxMessage);
                outboxRepository.updateWithStatus(outboxMessage.getId(), OutboxMessageStatus.SENT, Instant.now());
            } else {
                logger.error("Outbox process strategy not found for outboxMessageType={}, outboxMessageId={}",
                        outboxMessage.getType(), outboxMessage.getId());
                outboxRepository.updateWithStatus(outboxMessage.getId(), OutboxMessageStatus.FAILED, Instant.now());
            }
        } catch (Exception e) {
            logger.error("Failed to process outbox message outboxMessageId={}", outboxMessage.getId(), e);
            outboxRepository.updateWithStatus(outboxMessage.getId(), OutboxMessageStatus.FAILED, Instant.now());
        }
    }
}
