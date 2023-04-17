package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.repository.model.Client;

public interface TransactionalClientService {

  Client save(Client client);
}
