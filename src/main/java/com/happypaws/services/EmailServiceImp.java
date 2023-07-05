package com.happypaws.services;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class EmailServiceImp implements EmailService{

    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public EmailServiceImp(JavaMailSender javaMailSender, Environment environment) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(this.environment.getProperty("spring.mail.username")));
        message.setTo(to);
        message.setText(text);
        message.setSubject("contact email");
        javaMailSender.send(message);
    }

    @Override
    public String getHostAddress() {
        return Objects.requireNonNull(this.environment.getProperty("spring.mail.username"));
    }
}
