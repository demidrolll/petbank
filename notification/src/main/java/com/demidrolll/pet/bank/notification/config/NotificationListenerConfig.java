package com.demidrolll.pet.bank.notification.config;

import com.demidrolll.pet.bank.notification.listener.EmailNotificationListener;
import com.demidrolll.pet.bank.notification.service.EmailNotificationService;
import com.demidrolll.pet.bank.notification.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationListenerConfig {

  @Bean
  public NotificationService emailNotificationService() {
    return new EmailNotificationService();
  }

  @Bean
  public EmailNotificationListener emailNotificationListener() {
    return new EmailNotificationListener(emailNotificationService());
  }
}
