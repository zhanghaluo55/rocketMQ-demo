package com.hongpro.demo.rockmq.base.producter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RemotingServer;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangzihong
 * @version 1.0.0.0
 * @description 发送同步消息
 * @date 2021/5/21 18:11
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.56.103:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("base", "Tag1", ("Hello World" + i).getBytes());
            SendResult result = producer.send(message);
            SendStatus sendStatus = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("发送状态:" + sendStatus + "; 消息id:" + msgId + "; 队列id:" + queueId);

            TimeUnit.SECONDS.sleep(1);
        }

        producer.shutdown();
    }
}
