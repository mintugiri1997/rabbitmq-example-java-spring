package com.mintu.rabbitmqexample;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements ChannelAwareMessageListener {

    boolean acknowledge = false;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        if (message.toString() != null) {
            acknowledge = true;
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), acknowledge);
        String msgString = new String(message.getBody(),"UTF-8");
        Gson gson = new Gson();
        Post post = gson.fromJson(msgString,Post.class);
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection(Config.QUEUE).document().set(post);
        System.out.println("Request Consumed Successfully!: " + post);

        acknowledge = false;
    }
}
