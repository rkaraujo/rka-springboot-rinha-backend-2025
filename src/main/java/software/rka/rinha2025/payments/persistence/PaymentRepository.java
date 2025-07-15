package software.rka.rinha2025.payments.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import software.rka.rinha2025.payments.domain.Payment;
import software.rka.rinha2025.payments.util.MoneyUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class PaymentRepository {

    private static final String SQL_INSERT = "INSERT INTO payment (correlation_id, amount, created_at) VALUES (?, ?, ?) RETURNING id";
    private static final String SQL_INSERT_SUCCESS = "INSERT INTO payment_success (payment_id, amount, processor_default, created_at) VALUES (?, ?, ?, ?)";
    private static final String SQL_INSERT_FAIL = "INSERT INTO payment_fail (payment_id, amount, created_at) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Payment insert(Payment payment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, payment.getCorrelationId());
            ps.setLong(2, MoneyUtils.toCents(payment.getAmount()));
            ps.setTimestamp(3, Timestamp.from(payment.getCreatedAt()));
            return ps;
            }, keyHolder);

        return new Payment(
                keyHolder.getKey().longValue(),
                payment.getCorrelationId(),
                payment.getAmount(),
                payment.getCreatedAt()
        );
    }

    public void insertPaymentSuccess(Payment payment, boolean isPaymentProcessorDefault) {
        jdbcTemplate.update(SQL_INSERT_SUCCESS,
                payment.getId(),
                MoneyUtils.toCents(payment.getAmount()),
                isPaymentProcessorDefault,
                Timestamp.from(payment.getCreatedAt()));
    }

    public void insertPaymentFail(Payment payment) {
        jdbcTemplate.update(SQL_INSERT_FAIL,
                payment.getId(),
                MoneyUtils.toCents(payment.getAmount()),
                Timestamp.from(payment.getCreatedAt()));
    }
}
