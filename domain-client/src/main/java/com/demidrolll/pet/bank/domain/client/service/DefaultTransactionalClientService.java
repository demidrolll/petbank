package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.repository.ClientRepository;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DefaultTransactionalClientService implements TransactionalClientService {

  private final ClientRepository clientRepository;

  public DefaultTransactionalClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean save(Client client) {
    clientRepository.save(client);
    return true;
  }
}
