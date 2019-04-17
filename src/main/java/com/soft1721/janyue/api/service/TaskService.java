package com.soft1721.janyue.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
//@Async
public class TaskService {
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 46 9 ? * *")
    public void proces(){
        mailService.sendMail("16422802@qq.com","简单邮件","定时9.46发");
        System.out.println("111");
    }
}
