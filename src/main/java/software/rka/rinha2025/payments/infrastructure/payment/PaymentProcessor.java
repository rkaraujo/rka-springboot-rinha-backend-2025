package software.rka.rinha2025.payments.infrastructure.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentProcessor", url = "${payment-processors.default.host}")
public interface PaymentProcessor {

    @PostMapping("/payments")
    PaymentProcessorMessageResponse makePayment(PaymentProcessorRequest paymentRequest);

}
