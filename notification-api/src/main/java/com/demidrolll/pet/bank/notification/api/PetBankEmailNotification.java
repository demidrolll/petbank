package com.demidrolll.pet.bank.notification.api;

import java.util.List;
import java.util.Map;

public record PetBankEmailNotification(
    Long clientId,
    String email,
    String subject,
    String body,
    Map<String, String> bodyArgs,
    List<PetBankEmailAttachment> attachments
) implements PetBankNotification {

  @Override
  public Long clientId() {
    return clientId;
  }

  @Override
  public String recipient() {
    return email;
  }
}
