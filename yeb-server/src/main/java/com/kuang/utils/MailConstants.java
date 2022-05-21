package com.kuang.utils;
public class MailConstants {
    //表示待发送
    public static final Integer DELIVERY_PREPARE = 0;
    //表示发送成功
    public static final Integer DELIVERY_SUCCESS = 1;
    //表示发送失败
    public static final Integer DELIVERY_FAIL = 2;
    //最大的重试次数
    public static final Integer MAX_RETRY_COUNT = 3;
    //重试时间
    public static final Integer RETRY_TIME = 1;
    //交换机
    public static final String EXCHANGE = "mail.exchange";
    //队列
    public static final String QUEUE = "mail.queue";
    //路由
    public static final String ROUTING_KEY = "mail.routingkey";

}
