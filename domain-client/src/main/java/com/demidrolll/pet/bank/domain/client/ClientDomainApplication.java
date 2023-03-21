package com.demidrolll.pet.bank.domain.client;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientDomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientDomainApplication.class, args);
  }
}
