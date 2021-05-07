package com.mintu.rabbitmqexample.Consumers;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.mintu.rabbitmqexample.Config.Config;
import com.mintu.rabbitmqexample.Models.Events;
import com.mintu.rabbitmqexample.Models.Post;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements ChannelAwareMessageListener {

    boolean acknowledge = false;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        if (message.toString() != null) {
            acknowledge = true;
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), acknowledge);
        String msgString = new String(message.getBody(), "UTF-8");
        Gson gson = new Gson();
        if (msgString.startsWith("{\"req")) {
            Post post = gson.fromJson(msgString, Post.class);
            firestore.collection(Config.REQUEST_QUEUE).document().set(post);
            System.out.println("Request Consumed Successfully!: " + msgString);
        }
        if (msgString.startsWith("{\"name\"")) {
            Events events = gson.fromJson(msgString, Events.class);
            firestore.collection(Config.EVENTS_QUEUE).document().set(events);
            System.out.println("Event Consumed Successfully!: " + msgString);
        }
        acknowledge = false;
    }
}
