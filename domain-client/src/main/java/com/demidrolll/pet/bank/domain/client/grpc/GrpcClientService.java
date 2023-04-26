package com.demidrolll.pet.bank.domain.client.grpc;

import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceImplBase;
import com.demidrolll.pet.bank.domain.client.api.CreateClientRequest;
import com.demidrolll.pet.bank.domain.client.api.CreateClientResponse;
import com.demidrolll.pet.bank.domain.client.api.GetClientRequest;
import com.demidrolll.pet.bank.domain.client.api.GetClientResponse;
import com.demidrolll.pet.bank.domain.client.service.ClientMutationService;
import com.demidrolll.pet.bank.domain.client.service.ClientQueryService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcClientService extends ClientServiceImplBase {

  private final ClientMutationService mutationService;
  private final ClientQueryService queryService;

  public GrpcClientService(ClientMutationService mutationService, ClientQueryService queryService) {
    this.mutationService = mutationService;
    this.queryService = queryService;
  }

  @Override
  public void create(CreateClientRequest request, StreamObserver<CreateClientResponse> responseObserver) {
    mutationService.create(request)
        .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
  }

  @Override
  public void getById(GetClientRequest request, StreamObserver<GetClientResponse> responseObserver) {
    queryService.findById(request)
        .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
  }
}
