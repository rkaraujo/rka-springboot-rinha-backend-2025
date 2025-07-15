package software.rka.rinha2025.payments.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

    public static long toCents(BigDecimal amount) {
        return amount
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();
    }

    public static BigDecimal fromCents(long cents) {
        return BigDecimal.valueOf(cents)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
