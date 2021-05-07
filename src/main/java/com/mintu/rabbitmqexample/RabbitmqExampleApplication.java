package com.mintu.rabbitmqexample;

import com.mintu.rabbitmqexample.Config.Config;
import com.mintu.rabbitmqexample.Models.Events;
import com.mintu.rabbitmqexample.Models.Post;
import com.mintu.rabbitmqexample.Models.Request;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class RabbitmqExampleApplication implements CommandLineRunner {

    @Autowired
    private RabbitTemplate eventTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Request request = new Request();
        Post post = new Post();
        post.setRequest(request);
        post.setMessage("Processed");
        post.setStatus("Request Posted Successfully!");

        for (int i = 1; i <= 100; i++) {
            Random rand = new Random();
            // Generate random integers in range 0 to 999
            int amount = rand.nextInt(1000);
            request.setAmount(String.valueOf(amount));
            request.setId(UUID.randomUUID().toString());
            eventTemplate.convertAndSend(Config.EXCHANGE, Config.REQUEST_ROUTING_KEY, post);
            System.out.println("Request Sent Successfully.. : " + post);
        }

        Events events = new Events();
        for (int i = 1; i <= 100; i++){
            events.setId(UUID.randomUUID().toString());
            events.setName(UUID.randomUUID().toString());
            events.setTime(String.valueOf((new Date()).getTime()));

            eventTemplate.convertAndSend(Config.EXCHANGE,Config.EVENTS_ROUTING_KEY,events);
            System.out.println("Event Sent Successfully.. : " + events);
        }
        System.out.println("Task Completed!!!");

    }

}
