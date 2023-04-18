package com.demidrolll.pet.bank.notification.service;

import com.demidrolll.pet.bank.notification.api.PetBankEmailNotification;
import com.demidrolll.pet.bank.notification.api.PetBankNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class EmailNotificationService implements NotificationService {

  private final JavaMailSender mailSender;
  private final String senderAddress;
  private final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

  public EmailNotificationService(
      JavaMailSender mailSender,
      String senderAddress
  ) {
    this.mailSender = mailSender;
    this.senderAddress = senderAddress;
  }

  @Override
  public Mono<Void> send(PetBankNotification notification) {
    return Mono.fromCallable(() -> buildMessage((PetBankEmailNotification) notification))
        .delayUntil(msg ->
            Mono.fromRunnable(() -> mailSender.send(msg))
                .subscribeOn(Schedulers.boundedElastic())
        )
        .doOnNext(msg -> logger.info("Email sent: {}", notification))
        .then();
  }

  private SimpleMailMessage buildMessage(PetBankEmailNotification notification) {
    var message = new SimpleMailMessage();
    message.setFrom(senderAddress);
    message.setTo(notification.email());
    message.setSubject(notification.subject());
    message.setText(notification.body());
    return message;
  }
}
