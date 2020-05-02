package org.mvnsearch.memcached.reactive.spymemcached;

import net.spy.memcached.internal.BulkFuture;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Spymemcached Reactor Adapter
 *
 * @author linux_china
 */
public interface SpymemcachedAdapter {

    default Mono<Object> getFutureToMono(final GetFuture<Object> getFuture) {
        return Mono.create(monoSink -> {
            getFuture.addListener(future -> {
                if (future.isCancelled()) {
                    monoSink.error(new Exception("Cancelled"));
                } else {
                    if (future.getStatus().isSuccess()) {
                        monoSink.success(future.get());
                    } else {
                        monoSink.error(new Exception("Failed"));
                    }
                }
            });
        });
    }

    default <T> Mono<T> operationFutureToMono(OperationFuture<T> operationFuture) {
        return Mono.create(monoSink -> {
            operationFuture.addListener(future -> {
                if (future.isCancelled()) {
                    monoSink.error(new Exception("Cancelled"));
                } else {
                    monoSink.success((T) future.get());
                }
            });
        });
    }

    @SuppressWarnings("unchecked")
    default Flux<Map.Entry<String, Object>> bulkFutureToFlux(final BulkFuture<Map<String, Object>> bulkFuture) {
        return Flux.create(entryFluxSink -> {
            bulkFuture.addListener(future -> {
                if (future.getStatus().isSuccess()) {
                    ((Map<String, Object>) future.get()).entrySet().forEach(entryFluxSink::next);
                    entryFluxSink.complete();
                } else {
                    entryFluxSink.error(new Exception("Failed"));
                }
            });
        });
    }
}
