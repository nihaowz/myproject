package com.kuang.config.rabbitmqConfig;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.kuang.pojo.MailLog;
import com.kuang.service.IMailLogService;
import com.kuang.utils.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@Slf4j
public class RabbitmqConfig {
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;


    @Autowired
    IMailLogService mailLogService;

    /**
     * 消息确认回调
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 确认消息是否到达
         * data 消息的唯一标识
         * ack 确认结果
         * cause 失败的原因
         */
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            if(ack){
                log.info("发送成功"+data.getId());

                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",data.getId()));
            }else{
                log.error("消息发送失败========>"+cause);
            }
        });
        /**
         * 消息失败回调
         * routingKey 不到queue等等
         * msg 消息主题
         * repCode 消息响应码
         * repText 响应的描述
         * exchange 交换机
         * routingKey 路由跳转
         */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error(returnedMessage.getMessage().toString()+" "
                        +returnedMessage.getReplyText()+" "
                        +returnedMessage.getReplyCode());
            }
        });
        return rabbitTemplate;
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MailConstants.QUEUE);
    }

    /**
     * 声明交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.EXCHANGE);
    }

    /**
     * 交换机和队列进行绑定
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.ROUTING_KEY);
    }


}
