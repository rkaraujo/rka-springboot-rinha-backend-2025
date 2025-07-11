package software.rka.rinha2025.payments.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import software.rka.rinha2025.payments.domain.PaymentStatus;

import java.time.Instant;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Modifying
    @Transactional
    @Query("update PaymentEntity set status = :status, modifiedAt = :modifiedAt where id = :id")
    void updateWithStatus(Long id, PaymentStatus status, Instant modifiedAt);

}
