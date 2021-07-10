package com.hongpro.demo.rockmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangzihong
 * @version 1.0.0.0
 * @description
 * @date 2021/5/27 19:25
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.56.103:9876");
        producer.start();
        List<OrderStep> orderStepList = OrderStep.buildOrders();

        int index = 0;
        for(OrderStep orderStep : orderStepList) {
            Message msg = new Message("OrderTopic", "Order", "i" + (index++), orderStep.toString().getBytes());
            SendResult result = producer.send(msg,
                    new MessageQueueSelector() {
                        /**
                         *
                         * @param list 队列集合
                         * @param message 消息对象
                         * @param o 业务标识参数
                         * @return
                         */
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            long orderId = (long) o;
                            long index = orderId % list.size();
                            return list.get((int) index);
                        }
                    }, orderStep.getOrderId());
            System.out.println("发送结果" + result);
        }
        producer.shutdown();
    }
}
