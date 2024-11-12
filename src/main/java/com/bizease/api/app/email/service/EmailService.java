package com.bizease.api.app.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendPasswordResetEmail(String toEmail, String newPassword) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("Redefinição de Senha - BizEase");
        simpleMailMessage.setText("Olá,\n\nSua nova senha temporária é: " + newPassword +
                "\n\nPor favor, acesse o sistema e altere esta senha o mais rápido possível para garantir a segurança da sua conta." +
                "\n\nAtenciosamente,\nEquipe BizEase");

        mailSender.send(simpleMailMessage);
    }
}
