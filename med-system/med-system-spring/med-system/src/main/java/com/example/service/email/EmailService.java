package com.example.service.email;

import com.example.entity.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

//    public String sendSimpleMail(EmailDetails details) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMessageBody());
//            mailMessage.setSubject(details.getSubject());
//            javaMailSender.send(mailMessage);
//            return "Successfully sent";
//        } catch (Exception e) {
//            return "Error while sending mail";
//        }
//    }

    @Async
    public void sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMessageBody());
            mimeMessageHelper.setSubject(details.getSubject());
            ByteArrayResource file = new ByteArrayResource(Base64.getDecoder().decode(details.getAttachment()));
            mimeMessageHelper.addAttachment(details.getAttachmentName(), file);

            System.out.println(mimeMessage.getFileName());
            javaMailSender.send(mimeMessage);
            System.out.println("Successfully sent");
        } catch (MessagingException e) {
            System.out.println("Error while sending mail");
        }
    }
}
