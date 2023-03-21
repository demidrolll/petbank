package com.demidrolll.pet.bank.gateway.web.service;

import com.demidrolll.pet.bank.gateway.web.model.Client;
import reactor.core.publisher.Mono;

public interface ClientService {

  Mono<Client> getByPhoneNumber(String phone);
}
