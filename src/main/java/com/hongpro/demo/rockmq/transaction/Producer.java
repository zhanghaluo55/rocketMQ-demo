package com.hongpro.demo.rockmq.transaction;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
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
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("192.168.56.103:9876");
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 本地事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (StringUtils.equals("TAGA", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * MQ实现消息事务回查
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("消息Tag " + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();

        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (String tag : tags) {
            Message message = new Message("TransactionTag", tag, ("Hello World").getBytes());
            TransactionSendResult result = producer.sendMessageInTransaction(message, null);

            TimeUnit.SECONDS.sleep(1);
        }

    }
}
