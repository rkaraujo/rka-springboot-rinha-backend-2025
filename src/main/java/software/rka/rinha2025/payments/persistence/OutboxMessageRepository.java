package software.rka.rinha2025.payments.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import software.rka.rinha2025.payments.infrastructure.outbox.OutboxMessageStatus;

import java.time.Instant;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, Long> {

    Page<OutboxMessageEntity> findByStatusOrderByCreatedAt(OutboxMessageStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update OutboxMessageEntity set status = :status, modifiedAt = :modifiedAt where id = :id")
    void updateWithStatus(Long id, OutboxMessageStatus status, Instant modifiedAt);
}
