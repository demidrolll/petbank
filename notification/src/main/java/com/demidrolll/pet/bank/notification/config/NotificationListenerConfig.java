package com.demidrolll.pet.bank.notification.config;

import com.demidrolll.pet.bank.notification.listener.EmailNotificationListener;
import com.demidrolll.pet.bank.notification.service.EmailNotificationService;
import com.demidrolll.pet.bank.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class NotificationListenerConfig {

  @Value(value = "${notification.mail.sender}")
  private String senderEmail;
  @Autowired
  private JavaMailSender mailSender;

  @Bean
  public NotificationService emailNotificationService() {
    return new EmailNotificationService(mailSender, senderEmail);
  }

  @Bean
  public EmailNotificationListener emailNotificationListener() {
    return new EmailNotificationListener(emailNotificationService());
  }
}
