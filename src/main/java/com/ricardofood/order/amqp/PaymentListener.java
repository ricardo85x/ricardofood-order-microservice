package com.ricardofood.order.amqp;

import com.ricardofood.order.dto.PaymentDto;
import com.ricardofood.order.constants.PaymentQueueName;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = PaymentQueueName.PAYMENT_ORDER_INFO)
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
