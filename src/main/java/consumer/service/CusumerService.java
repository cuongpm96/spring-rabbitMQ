package consumer.service;

import consumer.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CusumerService  {

//    @Override
//    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
//
//    }

//    @RabbitListener(queues = {"com.demo.topic.queue1"})
//    public void receivedMessage(User user) {
//        System.out.println("debfuefe");
//        System.out.println(user);
//    }

    @RabbitListener(queues = {"com.demo.topic.queue1"})
    public void receivedMessage(User user) {
        System.out.println("deb");
        System.out.println(" eedwed" +user);
    }
}
