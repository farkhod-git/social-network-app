package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.service.EmailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements EmailSendingService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(String to, String subject, String body) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("farkhod.go@gmail.com");
        smm.setTo(to);
        smm.setSubject(subject);
        smm.setText(body);
        mailSender.send(smm);
    }
}
