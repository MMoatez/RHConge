package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsynchronizedTasksImpl implements AsynchronizedTasks{


    @Autowired
    private EmailService emailService;

    @Async
    @Override
    public void sendEmail(String to, String subject, String text) {
        this.emailService.sendEmail(to, subject, text);
    }
}
