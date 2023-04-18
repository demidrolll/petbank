package com.demidrolll.pet.bank.notification.service;

import com.demidrolll.pet.bank.notification.api.PetBankNotification;
import reactor.core.publisher.Mono;

public interface NotificationService {

  Mono<Void> send(PetBankNotification notification);
}
