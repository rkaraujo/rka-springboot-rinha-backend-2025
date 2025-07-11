package software.rka.rinha2025.payments.infrastructure.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentProcessorSecondary", url = "${payment-processors.secondary.host}")
public interface PaymentProcessorSecondary {

    @PostMapping("/payments")
    PaymentProcessorMessageResponse makePayment(PaymentProcessorRequest paymentRequest);

}
