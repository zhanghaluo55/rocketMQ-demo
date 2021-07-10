package com.hongpro.demo.rockmq.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangzihong
 * @version 1.0.0.0
 * @description
 * @date 2021/5/26 19:06
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.56.103:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("FilterTag", "Tag1", ("Hello World" + i).getBytes());
            //sql过滤
            message.putUserProperty("i", String.valueOf(i));

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果成功;" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送异常;" + throwable.getMessage());
                }
            });

            TimeUnit.SECONDS.sleep(1);
        }

        producer.shutdown();
    }
}
