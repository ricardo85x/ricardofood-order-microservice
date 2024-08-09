package com.ricardofood.order.amqp;

import com.ricardofood.order.dto.PaymentDto;
import com.ricardofood.order.enums.PaymentQueueName;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = PaymentQueueName.PAYMENT_COMPLETED)
    public void receiveMessage(PaymentDto paymentDto) {
        var message = """
            Payment Id: %s
            Order Number: %s
            Amount: R$: %s
            Status: %s
        """.formatted(
                paymentDto.getId(),
                paymentDto.getOrderId(),
                paymentDto.getAmount(),
                paymentDto.getStatus()
        );
        System.out.println("Received message: \n" + message);
    }
}
