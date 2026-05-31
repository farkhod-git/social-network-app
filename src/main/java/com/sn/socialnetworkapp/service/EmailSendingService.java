package com.sn.socialnetworkapp.service;

public interface EmailSendingService {

    void send(String to, String subject, String body);

}
