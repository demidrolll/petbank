package com.demidrolll.pet.bank.domain.client.service;

import com.demidrolll.pet.bank.domain.client.api.ClientRequest;
import com.demidrolll.pet.bank.domain.client.api.ClientResponse;
import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class DefaultClientService extends ClientServiceImplBase {

  @Override
  public void hello(ClientRequest request, StreamObserver<ClientResponse> responseObserver) {
    String greeting = "Hello, "
        + request.getFirstName()
        + " "
        + request.getLastName();

    responseObserver.onNext(ClientResponse.newBuilder().setGreeting(greeting).build());
    responseObserver.onCompleted();
  }
}
