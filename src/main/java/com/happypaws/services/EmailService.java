package com.happypaws.services;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    String getHostAddress();
}
