package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import reactor.core.publisher.Mono;

public interface ClientService {

  Mono<CreateClientResponse> createClient(CreateClientRequest request);
}
