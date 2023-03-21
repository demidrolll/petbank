package com.demidrolll.pet.bank.domain.client.config;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class,
    GrpcServerFactoryAutoConfiguration.class
})
public class GrpcServerConfiguration {
}
