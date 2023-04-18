package com.demidrolll.pet.bank.notification.listener;

import com.demidrolll.pet.bank.notification.api.PetBankNotification;
import com.demidrolll.pet.bank.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;

public class EmailNotificationListener {

  private final NotificationService notificationService;

  public EmailNotificationListener(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @KafkaListener(topics = "petbank-mail-notification", groupId = "notification")
  public void listenMailNotification(PetBankNotification notification) {
    notificationService.send(notification)
        .subscribe();
  }
}
