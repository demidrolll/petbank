package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.config.TransactionManager;
import com.demidrolll.pet.bank.domain.client.repository.ClientRepository;
import com.demidrolll.pet.bank.domain.client.repository.PersonalDataRepository;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = TransactionManager.MASTER_BEAN)
public class DefaultTransactionalClientService implements TransactionalClientService {

  private final ClientRepository clientRepository;
  private final PersonalDataRepository personalDataRepository;

  public DefaultTransactionalClientService(ClientRepository clientRepository,
      PersonalDataRepository personalDataRepository) {
    this.clientRepository = clientRepository;
    this.personalDataRepository = personalDataRepository;
  }

  @Override
  public Client save(Client client) {
    personalDataRepository.save(client.getPersonalData());
    return clientRepository.save(client);
  }
}
