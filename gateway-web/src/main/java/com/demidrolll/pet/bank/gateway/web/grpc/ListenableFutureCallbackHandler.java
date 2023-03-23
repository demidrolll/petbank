package com.demidrolll.pet.bank.gateway.web.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import reactor.core.publisher.Mono;

public interface ListenableFutureCallbackHandler {

  <T> Mono<T> execute(ListenableFuture<T> future);
}
