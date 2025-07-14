package software.rka.rinha2025.payments.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageStatus;

import java.time.Instant;
import java.util.List;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, Long> {

    @Query(value = """
            SELECT * FROM outbox
            WHERE status = 'NEW'
            ORDER BY created_at
            LIMIT :limit
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<OutboxMessageEntity> findNewOutboxMessages(int limit);

    @Modifying
    @Transactional
    @Query("update OutboxMessageEntity set status = :status, modifiedAt = :modifiedAt where id = :id")
    void updateWithStatus(Long id, OutboxMessageStatus status, Instant modifiedAt);
}
