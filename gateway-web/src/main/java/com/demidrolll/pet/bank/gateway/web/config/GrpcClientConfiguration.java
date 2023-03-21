package com.demidrolll.pet.bank.gateway.web.config;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(GrpcClientAutoConfiguration.class)
public class GrpcClientConfiguration {
}
