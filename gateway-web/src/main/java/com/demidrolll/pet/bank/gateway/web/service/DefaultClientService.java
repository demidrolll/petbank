package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import reactor.core.publisher.Mono;

public class DefaultClientService implements ClientService {

  @Override
  public Mono<CreateClientResponse> createClient(CreateClientRequest request) {
    return null;
  }
}
