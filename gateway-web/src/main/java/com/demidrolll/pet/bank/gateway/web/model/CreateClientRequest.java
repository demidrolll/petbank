package com.demidrolll.pet.bank.gateway.web.model;

import java.time.LocalDate;

public record CreateClientRequest(
    String firstName,
    String lastName,
    String middleName,
    Sex sex,
    LocalDate birthDate
) {

}
