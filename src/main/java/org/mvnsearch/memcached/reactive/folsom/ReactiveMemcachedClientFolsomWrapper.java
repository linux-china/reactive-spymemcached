package org.mvnsearch.memcached.reactive.folsom;

import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.MemcachedStats;
import org.mvnsearch.memcached.reactive.ReactiveMemcachedClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Reactive Memcached Client Folsom Wrapper
 *
 * @author linux_china
 */
public class ReactiveMemcachedClientFolsomWrapper<V> implements ReactiveMemcachedClient<V> {
    private MemcacheClient<V> rawClient;

    public ReactiveMemcachedClientFolsomWrapper(MemcacheClient<V> rawClient) {
        this.rawClient = rawClient;
    }

    @Override
    public Mono<MemcacheStatus> set(String key, V value, int ttl) {
        return Mono.fromCompletionStage(rawClient.set(key, value, ttl));
    }

    @Override
    public Mono<MemcacheStatus> set(String key, V value, int ttl, long cas) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> delete(String key) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> delete(String key, long cas) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> deleteAll(String key) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> add(String key, V value, int ttl) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> replace(String key, V value, int ttl) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> append(String key, V value) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> prepend(String key, V value) {
        return null;
    }

    @Override
    public Mono<V> get(String key) {
        return Mono.fromCompletionStage(rawClient.get(key));
    }

    @Override
    public Mono<GetResult<V>> casGet(String key) {
        return null;
    }

    @Override
    public Mono<List<V>> get(List<String> keys) {
        return null;
    }

    @Override
    public Mono<List<GetResult<V>>> casGet(List<String> keys) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> touch(String key, int ttl) {
        return null;
    }

    @Override
    public Mono<MemcacheStatus> flushAll(int delay) {
        return null;
    }

    @Override
    public Mono<Map<String, MemcachedStats>> getStats(String key) {
        return null;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public MemcacheClient<V> getRawClient() {
        return this.rawClient;
    }
}
