package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceFutureStub;
import com.demidrolll.pet.bank.domain.client.api.Sex;
import com.demidrolll.pet.bank.gateway.web.grpc.ListenableFutureCallbackHandler;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdData;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdRequest;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdResponse;
import com.demidrolll.pet.bank.gateway.web.model.LoginRequest;
import com.demidrolll.pet.bank.gateway.web.model.LoginResponse;
import com.demidrolll.pet.bank.gateway.web.model.Result;
import com.demidrolll.pet.bank.gateway.web.model.UserSession;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultClientService implements ClientService {

  private final ReactiveRedisTemplate<String, Object> redisTemplate;
  private final ClientServiceFutureStub clientServiceStub;
  private final ListenableFutureCallbackHandler callbackHandler;
  private final Logger logger = LoggerFactory.getLogger(DefaultClientService.class);

  public DefaultClientService(ClientServiceFutureStub clientServiceStub,
      ListenableFutureCallbackHandler callbackHandler, ReactiveRedisTemplate<String, Object> redisTemplate) {
    this.clientServiceStub = clientServiceStub;
    this.callbackHandler = callbackHandler;
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Mono<CreateClientResponse> createClient(CreateClientRequest request) {
    return Mono
        .fromCallable(() -> com.demidrolll.pet.bank.domain.client.api.CreateClientRequest.newBuilder()
            .setFirstName(request.firstName())
            .setLastName(request.lastName())
            .setMiddleName(Optional.ofNullable(request.middleName()).orElse(""))
            .setBirthDate(request.birthDate().toEpochDay())
            .setSex(Sex.valueOf(request.sex().name()))
            .build()
        )
        .flatMap(stubRequest -> callbackHandler.execute(clientServiceStub.create(stubRequest)))
        .map(response -> new CreateClientResponse(Result.valueOf(response.getResult().name())))
        .onErrorResume(ex -> {
          logger.error("Error while creating client", ex);
          return Mono.just(new CreateClientResponse(Result.FAIL));
        });
  }

  @Override
  public Mono<GetClientByIdResponse> getClientById(GetClientByIdRequest request) {
    return Mono
        .fromCallable(() -> com.demidrolll.pet.bank.domain.client.api.GetClientRequest.newBuilder()
            .setId(request.id())
            .build()
        )
        .flatMap(stubRequest -> callbackHandler.execute(clientServiceStub.getById(stubRequest)))
        .map(response -> new GetClientByIdResponse(
                Result.SUCCESS,
                new GetClientByIdData(
                    response.getFirstName(),
                    response.getLastName(),
                    response.getMiddleName()
                )
            )
        )
        .switchIfEmpty(Mono.fromCallable(() -> new GetClientByIdResponse(Result.NO_DATA, null)))
        .onErrorResume(ex -> {
          logger.error("Error while fetching client with id: {}", request.id(), ex);
          return Mono.just(new GetClientByIdResponse(Result.FAIL, null));
        });
  }

  @Override
  public Mono<LoginResponse> login(LoginRequest request) {
    return redisTemplate.opsForValue()
        .get(request.userId().toString())
        .switchIfEmpty(
            Mono.fromCallable(() -> new UserSession(UUID.randomUUID().toString()))
                .delayUntil(value ->
                    redisTemplate.opsForValue().set(request.userId().toString(), value, Duration.ofMinutes(1))
                )
        )
        .map(value -> {
          UserSession session = (UserSession) value;
          return new LoginResponse(session.id());
        });
  }
}
