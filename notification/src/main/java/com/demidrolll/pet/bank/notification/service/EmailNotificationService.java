package com.demidrolll.pet.bank.notification.service;

import com.demidrolll.pet.bank.notification.api.PetBankNotification;
import reactor.core.publisher.Mono;

public class EmailNotificationService implements NotificationService {

  @Override
  public Mono<Void> send(PetBankNotification notification) {
    return Mono
        .just(notification)
        .doOnNext((ntf) -> System.out.println("Received message: " + ntf))
        .then();
  }
}
