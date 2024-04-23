package com.yeva.dauletova.services;

import com.yeva.dauletova.models.EmailConfirmationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private JavaMailSender sender;

    public EmailSenderService(JavaMailSender sender) {
        this.sender = sender;
    }
    public void sendConfirmationEmail(EmailConfirmationToken token) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(token.getUser().getEmail());
        helper.setSubject("Confirm your email - ApiAgregator Registration");
        helper.setText("<a href=http://localhost:8080/auth/confirm-email?token="+token.getToken()+">Confirm email</a>", true);
        helper.setFrom("<no-reply>dauletova.yeva@gmail.com");
        sender.send(message);
    }
}
