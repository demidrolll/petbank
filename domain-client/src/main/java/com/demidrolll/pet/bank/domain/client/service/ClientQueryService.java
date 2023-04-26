package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.api.GetClientRequest;
import com.demidrolll.pet.bank.domain.client.api.GetClientResponse;
import reactor.core.publisher.Mono;

public interface ClientQueryService {

  Mono<GetClientResponse> findById(GetClientRequest request);
}
