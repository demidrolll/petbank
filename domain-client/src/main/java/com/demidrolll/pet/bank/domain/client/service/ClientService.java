package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.api.CreateClientRequest;
import com.demidrolll.pet.bank.domain.client.api.CreateClientResponse;
import reactor.core.publisher.Mono;

public interface ClientService {

  Mono<CreateClientResponse> create(CreateClientRequest request);
}
