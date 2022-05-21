package com.kuang.config.MailTaskConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.MailLog;
import com.kuang.service.IAdminRoleService;
import com.kuang.service.IAdminService;
import com.kuang.service.IMailLogService;
import com.kuang.utils.MailConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MailTaskScheduled {

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        List<MailLog> mailLogs = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        mailLogs.forEach(mailLog -> {
            if(mailLog.getCount() >= 3){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.RETRY_TIME)).set("updateTime",LocalDateTime.now()).set("count",mailLog.getCount()+1).eq("msgId",mailLog.getMsgId()));

            List<Admin> admins = adminService.list(new QueryWrapper<Admin>().eq("id", mailLog.getEid()));
            Admin admin = admins.get(0);
            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE,MailConstants.ROUTING_KEY,admin,new CorrelationData(mailLog.getMsgId()));
        });

    }


}
