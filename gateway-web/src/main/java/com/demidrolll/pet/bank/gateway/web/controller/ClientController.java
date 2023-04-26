package com.demidrolll.pet.bank.gateway.web.controller;

import com.demidrolll.pet.bank.gateway.web.model.CreateClientRequest;
import com.demidrolll.pet.bank.gateway.web.model.CreateClientResponse;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdRequest;
import com.demidrolll.pet.bank.gateway.web.model.GetClientByIdResponse;
import com.demidrolll.pet.bank.gateway.web.service.ClientService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @MutationMapping("createClient")
  public Mono<CreateClientResponse> createClient(@Argument("request") CreateClientRequest request) {
    return clientService.createClient(request);
  }

  @QueryMapping("getClientById")
  public Mono<GetClientByIdResponse> getClientById(@Argument("request") GetClientByIdRequest request) {
    return clientService.getClientById(request);
  }
}
