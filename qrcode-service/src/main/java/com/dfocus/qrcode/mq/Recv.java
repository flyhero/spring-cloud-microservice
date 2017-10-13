package com.dfocus.qrcode.mq;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.support.Delivery;

import java.io.IOException;

/**
 * User: qfwang
 * Date: 2017-09-28
 * Time: 上午10:48
 */
public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void recvMsg(String queueName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        String a=channel.basicConsume(queueName, false, consumer);
        System.out.println("================"+a);


        //channel.queueDelete(queueName);
        channel.close();
        connection.close();
    }
}
