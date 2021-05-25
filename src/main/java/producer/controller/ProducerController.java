package producer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import producer.model.User;
import producer.service.RabbitMQSender;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ProducerController {

    @Resource(name = "rabbit_sender")
    private RabbitMQSender rabbitMQSender;

    @PostMapping("/user")
    public String publishUserDetails(@RequestBody User user) {
        rabbitMQSender.send(user);
        return "send success";
    }
}
