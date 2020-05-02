package org.mvnsearch.memcached.reactive.folsom;

import com.spotify.folsom.BinaryMemcacheClient;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;

/**
 * Reactive Folsom test
 *
 * @author linux_china
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReactiveFolsomTest {
    private ReactiveMemcachedClientFolsomWrapper<String> reactiveFolsomClient;

    @BeforeAll
    public void setUp() throws Exception {
        BinaryMemcacheClient<String> folsomClient = MemcacheClientBuilder.newStringClient()
                .withAddress("127.0.0.1")
                .connectBinary();
        ConnectFuture.connectFuture(folsomClient).toCompletableFuture().get();
        reactiveFolsomClient = new ReactiveMemcachedClientFolsomWrapper<>(folsomClient);
    }

    @AfterAll
    public void tearDown() {
        reactiveFolsomClient.getRawClient().shutdown();
    }

    @Test
    public void testSetAndGet() {
       Mono<String> result =  reactiveFolsomClient.set("nick","leijuan",10000)
                .then(reactiveFolsomClient.get("nick"));
        System.out.println(result.block());
        
    }
}
