package com.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = {"com.demo"})
public class Config {

    @Bean
    public ApplicationRunner runner(RabbitTemplate template) {
        return args -> {
            template.convertAndSend("myQueue1", "user");
        };
    }

    @Bean
    public ApplicationRunner runner1(RabbitTemplate template) {
        return args -> {
            template.convertAndSend("com.demo.topic.exchange", "user.important.error", "alo");
            template.convertAndSend("com.demo.topic.queue1", "demuden");
        };
    }

    @Bean public Queue myQueue() {
        return new Queue("myQueue", false);
    }

    @RabbitListener(queues = "myQueue")
    public void listen(String in) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println(" message read from myQueue : " + in);
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue queue1 = new Queue("com.demo.faout.queue1", false);
        Queue queue2 = new Queue("com.demo.faout.queue2", false);
        FanoutExchange fanoutExchange = new FanoutExchange("com.demo.faout.exchange");

        return new Declarables(
                queue1,
                queue2,
                fanoutExchange,
                BindingBuilder.bind(queue1).to(fanoutExchange),
                BindingBuilder.bind(queue2).to(fanoutExchange));
    }

    @Bean
    public Declarables topicBindings() {
        Queue queue1 = new Queue("com.demo.topic.queue1", false);
        Queue queue2 = new Queue("com.demo.topic.queue2", false);

        TopicExchange topicExchange = new TopicExchange("com.demo.topic.exchange");
        return new Declarables(
                queue1,
                queue2,
                topicExchange,
                BindingBuilder.bind(queue1).to(topicExchange).with("*.inportant.*"),
                BindingBuilder.bind(queue2).to(topicExchange).with("#.error"));
    }

}
