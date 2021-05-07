package com.mintu.rabbitmqexample.Publishers;

import com.mintu.rabbitmqexample.Models.Request;
import com.mintu.rabbitmqexample.Models.Status;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/request")
public class Publisher {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{requestId}")
    public String postRequest(@RequestBody Request request, @PathVariable String requestId) {
        request.setId(UUID.randomUUID().toString());

        Status status = new Status(request, "Processed", "Request posted successfully!");

        template.convertAndSend("exchange", "routing_key", status);

        return "Success";
    }


}
