package org.mvnsearch.memcached.reactive.spymemcached;

import net.spy.memcached.MemcachedClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

/**
 * Reactive Memcached Client
 *
 * @author linux_china
 */
public class ReactiveMemcachedClient implements SpymemcachedAdapter {
    private final MemcachedClient memcachedClient;

    public ReactiveMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    Mono<Boolean> set(final String key, final int exp, final Object o) {
        return operationFutureToMono(memcachedClient.set(key, exp, o));
    }

    Mono<Object> get(final String key) {
        return getFutureToMono(memcachedClient.asyncGet(key));
    }

    Flux<Map.Entry<String, Object>> getBulk(Collection<String> keys) {
        return bulkFutureToFlux(memcachedClient.asyncGetBulk(keys));
    }

    Flux<Map.Entry<String, Object>> getBulk(String... keys) {
        return bulkFutureToFlux(memcachedClient.asyncGetBulk(keys));
    }

    Mono<Boolean> delete(String key) {
        return operationFutureToMono(memcachedClient.delete(key));
    }

    Mono<Boolean> touch(final String key, final int exp) {
        return operationFutureToMono(memcachedClient.touch(key, exp));
    }
}
