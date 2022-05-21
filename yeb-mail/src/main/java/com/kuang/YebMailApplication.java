package com.kuang;

import com.kuang.utils.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class YebMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebMailApplication.class,args);
    }

    @Bean
    public Queue Queue(){
        return new Queue(MailConstants.QUEUE);
    }

}
