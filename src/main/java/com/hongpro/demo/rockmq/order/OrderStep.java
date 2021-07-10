package com.hongpro.demo.rockmq.order;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzihong
 * @version 1.0.0.0
 * @description
 * @date 2021/5/27 17:47
 */
@Builder
public class OrderStep {
    private long orderId;
    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static List<OrderStep> buildOrders() {
        List<OrderStep> orderStepList = new ArrayList<>();
        orderStepList.add(OrderStep.builder().orderId(1039L).desc("创建").build());
        orderStepList.add(OrderStep.builder().orderId(1040L).desc("付款").build());
        orderStepList.add(OrderStep.builder().orderId(1041L).desc("完成").build());
        orderStepList.add(OrderStep.builder().orderId(2039L).desc("创建").build());
        orderStepList.add(OrderStep.builder().orderId(2040L).desc("付款").build());
        orderStepList.add(OrderStep.builder().orderId(2041L).desc("完成").build());
        return orderStepList;
    }
}
