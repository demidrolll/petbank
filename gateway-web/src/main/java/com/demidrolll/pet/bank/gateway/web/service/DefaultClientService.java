package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceFutureStub;
import com.demidrolll.pet.bank.domain.client.api.Sex;
import com.demidrolll.pet.bank.gateway.web.grpc.ListenableFutureCallbackHandler;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import com.demidrolll.pet.bank.gateway.web.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class DefaultClientService implements ClientService {

  private final ClientServiceFutureStub clientServiceStub;
  private final ListenableFutureCallbackHandler callbackHandler;
  private final Logger logger = LoggerFactory.getLogger(DefaultClientService.class);

  public DefaultClientService(ClientServiceFutureStub clientServiceStub,
      ListenableFutureCallbackHandler callbackHandler) {
    this.clientServiceStub = clientServiceStub;
    this.callbackHandler = callbackHandler;
  }

  @Override
  public Mono<CreateClientResponse> createClient(CreateClientRequest request) {
    var stubRequest = com.demidrolll.pet.bank.domain.client.api.CreateClientRequest.newBuilder()
        .setFirstName(request.firstName())
        .setLastName(request.lastName())
        .setMiddleName(request.middleName())
        .setBirthDate(request.birthDate().toEpochDay())
        .setSex(Sex.valueOf(request.sex().name()))
        .build();

    return callbackHandler.execute(clientServiceStub.create(stubRequest))
        .map(response -> new CreateClientResponse(Result.SUCCESS))
        .onErrorResume(ex -> {
          logger.error("Error while creating client", ex);
          return Mono.just(new CreateClientResponse(Result.FAIL));
        });
  }
}
