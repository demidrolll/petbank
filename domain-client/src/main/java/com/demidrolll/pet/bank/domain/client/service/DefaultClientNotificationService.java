package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.repository.ClientRepository;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import com.demidrolll.pet.bank.notification.api.PetBankEmailNotification;
import com.demidrolll.pet.bank.notification.api.PetBankNotification;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultClientNotificationService implements ClientNotificationService {

  private final KafkaTemplate<String, PetBankNotification> kafkaTemplate;
  private final ClientRepository clientRepository;
  @Value(value = "${notification.mail.topic}")
  private String mailTopicName;

  public DefaultClientNotificationService(KafkaTemplate<String, PetBankNotification> kafkaTemplate,
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
                new PetBankEmailNotification(
                    clientId,
                    "client@gmail.com",
                    "subject",
                    "Welcome to PetBank, ${FIRST_NAME}",
                    Map.of("FIRST_NAME", personalData.getFirstName()),
                    List.of()
                )
            )
        ))
        .map(result -> true)
        .onErrorReturn(false);
  }
}
