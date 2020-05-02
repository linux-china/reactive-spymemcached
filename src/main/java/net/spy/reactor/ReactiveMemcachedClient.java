package net.spy.reactor;

import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.MemcachedStats;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * Reactive Memcached Client
 *
 * @author linux_china
 */
public interface ReactiveMemcachedClient<V> {

    CompletionStage<MemcacheStatus> set(String key, V value, int ttl);

    /**
     * Compare and set a key in memcache to the provided value, with the specified TTL
     *
     * @param key   The key, must not be null
     * @param value The value, must not be null
     * @param ttl   The TTL in seconds
     * @param cas   The CAS value, must match the value on the server for the set to go through
     * @return A future representing completion of the request.
     */
    Mono<MemcacheStatus> set(String key, V value, int ttl, long cas);

    /**
     * Delete the provided key
     *
     * @param key Key, must not be null
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> delete(String key);

    /**
     * Deletes a key with CAS check.
     *
     * @param key The key, must not be null
     * @param cas The CAS value, must match the value on the server for the set to go through
     * @return A future representing completion of the request.
     */
    Mono<MemcacheStatus> delete(String key, long cas);

    /**
     * Delete the provided key on all memcached instances. This is typically only useful for a
     * multi-instance setup (using Ketama).
     *
     * @param key Key, must not be null
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> deleteAll(String key);

    /**
     * Add a key in memcache with the provided value, with the specified TTL. Key must not exist in
     * memcache
     *
     * @param key   The key, must not be null
     * @param value The value, must not be null
     * @param ttl   The TTL in seconds
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> add(String key, V value, int ttl);

    /**
     * Replace a key in memcache with the provided value, with the specified TTL. Key must exist in
     * memcache
     *
     * @param key   The key, must not be null
     * @param value The value, must not be null
     * @param ttl   The TTL in seconds
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> replace(String key, V value, int ttl);

    Mono<MemcacheStatus> append(String key, V value);

    Mono<MemcacheStatus> prepend(String key, V value);

    /**
     * Get the value for the provided key
     *
     * @param key The key, must not be null
     * @return A future representing completion of the request, with the value, or null if the key
     * does not exist
     */
    Mono<V> get(String key);

    /**
     * Get the value for the provided key, including the CAS value
     *
     * @param key First key, must not be null
     * @return A future representing completion of the request, with the value, including the CAS
     * value, or null if the value does not exists.
     */
    Mono<GetResult<V>> casGet(String key);

    /**
     * Get the value for the provided keys
     *
     * @param keys Keys, must not be null, nor must any key in the list
     * @return A future representing completion of the request, with the values. Any non existing
     * values will be null. Order will be maintained from the input keys
     */
    Mono<List<V>> get(List<String> keys);

    /**
     * Get the value for the provided keys
     *
     * @param keys Keys, must not be null, nor must any key in the list
     * @return A future representing completion of the request, with the values, including the CAS
     * value. Any non existing values will be null. Order will be maintained from the input keys
     */
    Mono<List<GetResult<V>>> casGet(List<String> keys);

    /**
     * Sets the expiration for the provided key
     *
     * @param key First key, must not be null
     * @param ttl The TTL in seconds
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> touch(String key, int ttl);

    /**
     * Flushes all entries in the storage
     *
     * @param delay The flush delay in seconds.
     * @return A future representing completion of the request
     */
    Mono<MemcacheStatus> flushAll(int delay);

    /**
     * Get raw statistics from the memcached instances. The return type is a map from hostname:port to
     * a statistics container.
     *
     * <p>The reason for including potentially multiple statistics containers is to support the ketama
     * use-case where there may be multiple memcached instances.
     *
     * <p>If any of the instances fail to return statistics, the exception will be propagated.
     *
     * <p>Valid keys are:
     *
     * <ul>
     *   <li>Empty string: ""
     *   <li>"slabs"
     *   <li>"sizes"
     *   <li>"items"
     *   <li>"settings"
     * </ul>
     *
     * @param key - statistics key.
     * @return a map of hostname:port to a statistics container
     */
    Mono<Map<String, MemcachedStats>> getStats(String key);

    /**
     * Shut down the client.
     */
    void shutdown();

    /**
     * Note: This is typically only useful for testing and debugging
     *
     * @return the underlying raw memcache client.
     */
    Object getRawClient();
}
