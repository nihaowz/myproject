package com.kuang.config.kaptchaConfig;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer(){
//        https://blog.csdn.net/Du_ZiLin/article/details/77367978?locationNum=1&fps=1
        //验证码代码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //是否有边框
        properties.put("kaptcha.border","yes");
        //设置边框颜色
        properties.put("kaptcha.border.color","black");
        //设置字体颜色
        properties.put("kaptcha.textproducer.font.color","black");
        //字体之间的间距
        properties.put("kaptcha.textproducer.char.space","5");
        //字体有多少个
        properties.put("kaptcha.textproducer.char.length","4");

        Config config = new Config(properties);

        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
