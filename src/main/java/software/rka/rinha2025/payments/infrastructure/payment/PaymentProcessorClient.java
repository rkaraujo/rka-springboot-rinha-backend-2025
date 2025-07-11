package software.rka.rinha2025.payments.infrastructure.payment;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorClient {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessorClient.class);

    private final PaymentProcessor paymentProcessor;
    private final PaymentProcessorSecondary paymentProcessorSecondary;

    public PaymentProcessorClient(PaymentProcessor paymentProcessor, PaymentProcessorSecondary paymentProcessorSecondary) {
        this.paymentProcessor = paymentProcessor;
        this.paymentProcessorSecondary = paymentProcessorSecondary;
    }

    public PaymentProcessorMessageResponse makePayment(PaymentProcessorRequest paymentRequest) {
        logger.info("Calling make payment service, request={}", paymentRequest);

        try {
            return paymentProcessor.makePayment(paymentRequest);
        } catch (Exception e) {
            // the FeignException comes wrapped by another Exception when using a circuit breaker
            FeignException feignException = unwrapFeignException(e);
            if (feignException == null) {
                throw new PaymentProcessorException(e);
            }

            // if it's an issue with the request and not the server, don't call the fallback
            if (feignException.status() >= 400 && feignException.status() <= 499) {
                throw new PaymentProcessorException(feignException);
            }

            // if server error, call fallback
            return makePaymentFallback(paymentRequest);
        }
    }

    private PaymentProcessorMessageResponse makePaymentFallback(PaymentProcessorRequest paymentRequest) {
        logger.info("Calling make payment fallback service, request={}", paymentRequest);

        try {
            return paymentProcessorSecondary.makePayment(paymentRequest);
        } catch (Exception e) {
            FeignException feignException = unwrapFeignException(e);
            throw new PaymentProcessorException(feignException != null ? feignException : e);
        }
    }

    private FeignException unwrapFeignException(Exception e) {
        FeignException feignException = null;
        if (e instanceof FeignException) {
            feignException = (FeignException) e;
        } else if (e.getCause() instanceof FeignException) {
            feignException = (FeignException) e.getCause();
        }
        return feignException;
    }
}
