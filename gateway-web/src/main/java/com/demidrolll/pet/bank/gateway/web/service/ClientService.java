package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdRequest;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdResponse;
import com.demidrolll.pet.bank.gateway.web.model.LoginRequest;
import com.demidrolll.pet.bank.gateway.web.model.LoginResponse;
import reactor.core.publisher.Mono;

public interface ClientService {

  Mono<CreateClientResponse> createClient(CreateClientRequest request);

  Mono<GetClientByIdResponse> getClientById(GetClientByIdRequest request);

  Mono<LoginResponse> login(LoginRequest request);
}
