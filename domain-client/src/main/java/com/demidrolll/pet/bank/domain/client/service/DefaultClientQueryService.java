package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.api.GetClientRequest;
import com.demidrolll.pet.bank.domain.client.api.GetClientResponse;
import com.demidrolll.pet.bank.domain.client.config.TransactionManager;
import com.demidrolll.pet.bank.domain.client.repository.ClientSlaveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true, transactionManager = TransactionManager.SLAVE_BEAN)
public class DefaultClientQueryService implements ClientQueryService {

  private final ClientSlaveRepository clientRepository;
  private final Logger logger = LoggerFactory.getLogger(DefaultClientQueryService.class);

  public DefaultClientQueryService(ClientSlaveRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Mono<GetClientResponse> findById(GetClientRequest request) {
    return Mono.fromCallable(() -> clientRepository.findById(request.getId()).orElse(null))
        .flatMap(client -> Mono.fromCallable(client::getPersonalData))
        .map(personalData ->
            GetClientResponse.newBuilder()
                .setFirstName(personalData.getFirstName())
                .setLastName(personalData.getLastName())
                .setMiddleName(personalData.getMiddleName())
                .build()
        )
        .onErrorResume(ex -> {
          logger.error("Error while fetching client: ", ex);
          return Mono.error(ex);
        });
  }
}
