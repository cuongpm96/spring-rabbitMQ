package producer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import producer.model.User;

import javax.annotation.Resource;

@Service("rabbit_sender")
public class RabbitMQSender {
    @Resource(name = "producer_template")
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange}")
    private String exchange;

    @Value("${rabbit.routingKey}")
    private String routeKey;

    public void send(User user) {
        rabbitTemplate.convertAndSend(exchange, routeKey,user);
    }
}
