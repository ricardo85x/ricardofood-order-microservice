package com.ricardofood.order.amqp;

import com.ricardofood.order.enums.PaymentQueueName;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = PaymentQueueName.PAYMENT_COMPLETED)
    public void receiveMessage(Message message) {
        System.out.println("Received message: " + new String(message.getBody()));
    }
}
