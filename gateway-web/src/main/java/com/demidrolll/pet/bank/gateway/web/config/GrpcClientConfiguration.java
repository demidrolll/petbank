package com.demidrolll.pet.bank.gateway.web.config;

import com.demidrolll.pet.bank.domain.client.api.ClientServiceGrpc.ClientServiceFutureStub;
import com.demidrolll.pet.bank.gateway.web.grpc.GrpcCallbackHandler;
import com.demidrolll.pet.bank.gateway.web.grpc.ListenableFutureCallbackHandler;
import java.util.concurrent.Executors;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(GrpcClientAutoConfiguration.class)
public class GrpcClientConfiguration {

  @Bean
  public ClientServiceFutureStub clientServiceStub(@GrpcClient("client-grpc-server") ClientServiceFutureStub stub) {
    return stub;
  }

  @Bean
  public ListenableFutureCallbackHandler grpcCallbackHandler() {
    return new GrpcCallbackHandler(Executors.newFixedThreadPool(16));
  }
}
