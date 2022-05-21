package com.kuang;


import com.kuang.pojo.Admin;
import com.kuang.utils.MailConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Date;

/**
 * 用于接受发送过来的消息
 */
@Component
@Slf4j
public class MailReceive {
    //邮件发送
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    //邮件配置
    private MailProperties mailProperties;
    //引擎
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    //监听
    @RabbitListener(queues = MailConstants.QUEUE)
    public void handler(Message message, Channel channel){
        Admin admin = (Admin) message.getPayload();
        long deliveryTag = (long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);//发送序列号
        String msgId = (String) message.getHeaders().get("spring_returned_message_correlation"); //拿到其序列号

        HashOperations hashOperations = redisTemplate.opsForHash();
        try {

            if(hashOperations.entries("mail_log").containsKey(msgId)){
                log.error("邮件已经被发送成功======>已经被消费");
                channel.basicAck(deliveryTag,false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo("1102164503@qq.com");
            helper.setSubject("欢迎注册该网站");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",admin.getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功");
            hashOperations.put("mail_log",msgId,"ok");
            //单条确认
            channel.basicAck(deliveryTag,false);

        } catch (Exception e) {
            /**
             * 手动确认消息
             * 第一个参数就是消息回调的序列号
             * 第二个就是消息的是否确认多条
             * 第三个就是失败的消息是否重回队列
             */
            try {
                channel.basicNack(deliveryTag,false,true);
            } catch (IOException ex) {
                log.error("邮件发送失败======>{}",e.getMessage());
            }
        }
    }

}
