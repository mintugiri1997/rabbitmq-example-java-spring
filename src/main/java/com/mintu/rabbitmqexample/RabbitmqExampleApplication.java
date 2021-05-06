package com.mintu.rabbitmqexample;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class RabbitmqExampleApplication implements CommandLineRunner {

    @Autowired
    private RabbitTemplate template;

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

        for (int i = 1; i <= 1000; i++) {
            Random rand = new Random();
            // Generate random integers in range 0 to 999
            int amount = rand.nextInt(1000);
            request.setAmount(String.valueOf(amount));
            request.setId(UUID.randomUUID().toString());
            template.convertAndSend("exchange", "routing_key", post);
            System.out.println("Message Sent Successfully.." + post);
        }
        System.out.println("Task Completed!!!");

    }

}
