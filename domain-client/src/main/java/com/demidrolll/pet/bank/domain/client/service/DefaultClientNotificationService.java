package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.repository.ClientRepository;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultClientNotificationService implements ClientNotificationService {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ClientRepository clientRepository;
  @Value(value = "${notification.mail.topic}")
  private String mailTopicName;

  public DefaultClientNotificationService(KafkaTemplate<String, String> kafkaTemplate,
      ClientRepository clientRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.clientRepository = clientRepository;
  }

  @Override
  public Mono<Boolean> sendWelcomeEmail(Long clientId) {
    return Mono
        .defer(() -> Mono.justOrEmpty(clientRepository.findById(clientId)))
        .map(Client::getPersonalData)
        .flatMap(personalData -> Mono.fromFuture(
            kafkaTemplate.send(
                mailTopicName,
                "Welcome to PetBank, " + personalData.getFirstName()
            )
        ))
        .map(result -> true)
        .onErrorReturn(false);
  }
}
