package com.dfocus.qrcode.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: qfwang
 * Date: 2017-09-28
 * Time: 上午10:45
 */
@Component
public class Send {
    private final static String QUEUE_NAME = "hello";

    @Autowired
    private RabbitManagementTemplate rabbitManagementTemplate;

    public void createQueue(String queueName)  throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.close();
        connection.close();
    }

    /**
     * 是否存在这个队列
     * @param queueName
     * @return
     */
    public boolean isExistsClient(String queueName){

        boolean flag = false;
        for (Queue q :rabbitManagementTemplate.getQueues()){
            System.out.println(q.getName());
            if (queueName.equals(q.getName())) {
                flag = true;
                break;
            }
        }
        return true;
    }

    public void sendMsg(String queueName,String content)  throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        if(isExistsClient(queueName)){
            channel.basicPublish("", queueName, null, content.getBytes("UTF-8"));
        }



        /**
         * 注1：queueDeclare第一个参数表示队列名称、
         * 第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
         * 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、
         * 第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
         * 第五个参数为队列的其他参数
         *
         * 注2：basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体
         */
        //channel.queueDelete(QUEUE_NAME);
        channel.close();
        connection.close();

    }
}
