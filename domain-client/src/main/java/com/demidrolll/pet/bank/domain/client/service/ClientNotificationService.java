package com.demidrolll.pet.bank.domain.client.service;

import reactor.core.publisher.Mono;

public interface ClientNotificationService {

  Mono<Boolean> sendWelcomeEmail(Long clientId);
}
