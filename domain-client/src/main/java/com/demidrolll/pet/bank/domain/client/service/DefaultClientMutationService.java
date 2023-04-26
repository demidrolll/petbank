package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.api.CreateClientRequest;
import com.demidrolll.pet.bank.domain.client.api.CreateClientResponse;
import com.demidrolll.pet.bank.domain.client.api.Result;
import com.demidrolll.pet.bank.domain.client.model.Sex;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import com.demidrolll.pet.bank.domain.client.repository.model.PersonalData;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class DefaultClientMutationService implements ClientMutationService {

  private final TransactionalClientService transactionalService;
  private final ClientNotificationService notificationService;
  private final Logger logger = LoggerFactory.getLogger(DefaultClientMutationService.class);

  public DefaultClientMutationService(TransactionalClientService transactionalService,
      ClientNotificationService notificationService) {
    this.transactionalService = transactionalService;
    this.notificationService = notificationService;
  }

  @Override
  public Mono<CreateClientResponse> create(CreateClientRequest request) {
    return Mono
        .fromCallable(() -> {
          var client = new Client();
          var personalData = new PersonalData();
          personalData.setFirstName(request.getFirstName());
          personalData.setLastName(request.getLastName());
          personalData.setMiddleName(request.getMiddleName());
          personalData.setSex(Sex.valueOf(request.getSex().name()));
          personalData.setBirthDate(LocalDate.ofEpochDay(request.getBirthDate()));
          client.setPersonalData(personalData);
          return client;
        })
        .flatMap(client -> Mono
            .fromCallable(() -> transactionalService.save(client))
            .subscribeOn(Schedulers.boundedElastic())
        )
        .delayUntil(client -> notificationService.sendWelcomeEmail(client.getId()))
        .map(client -> Result.SUCCESS)
        .onErrorResume(ex -> {
          logger.error("Error while creating user", ex);
          return Mono.just(Result.FAIL);
        })
        .map(result -> CreateClientResponse.newBuilder().setResult(result).build());
  }
}
