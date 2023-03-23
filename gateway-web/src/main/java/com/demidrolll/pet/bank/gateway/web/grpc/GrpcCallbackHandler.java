package com.demidrolll.pet.bank.gateway.web.grpc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutorService;
import reactor.core.publisher.Mono;

public final class GrpcCallbackHandler implements ListenableFutureCallbackHandler {

  private final ExecutorService executorService;

  public GrpcCallbackHandler(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public <T> Mono<T> execute(ListenableFuture<T> future) {
    return Mono.create(sink ->
        Futures.addCallback(
            future,
            new FutureCallback<>() {
              @Override
              public void onSuccess(T result) {
                sink.success(result);
              }

              @Override
              public void onFailure(Throwable t) {
                sink.error(t);
              }
            },
            executorService
        )
    );
  }
}
