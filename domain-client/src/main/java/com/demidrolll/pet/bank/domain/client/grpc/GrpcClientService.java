package com.demidrolll.pet.bank.domain.client.grpc;

import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceImplBase;
import com.demidrolll.pet.bank.domain.client.api.CreateClientRequest;
import com.demidrolll.pet.bank.domain.client.api.CreateClientResponse;
import com.demidrolll.pet.bank.domain.client.service.ClientService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcClientService extends ClientServiceImplBase {

  private final ClientService clientService;

  public GrpcClientService(ClientService clientService) {
    this.clientService = clientService;
  }

  @Override
  public void create(CreateClientRequest request, StreamObserver<CreateClientResponse> responseObserver) {
    clientService.create(request)
            .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
  }
}
