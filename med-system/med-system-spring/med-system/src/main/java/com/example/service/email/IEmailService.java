package com.example.service.email;

import com.example.entity.EmailDetails;

public interface IEmailService {
    void sendMailWithAttachment(EmailDetails details);
}
