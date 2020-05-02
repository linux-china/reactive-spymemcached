package org.mvnsearch.memcached.reactive.folsom;

import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Folsom test
 *
 * @author linux_china
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FolsomTest {
    private MemcacheClient<String> client;

    @BeforeAll
    public void setUp() throws Exception {
        client = MemcacheClientBuilder.newStringClient()
                .withAddress("127.0.0.1")
                .connectBinary();
        ConnectFuture.connectFuture(client).toCompletableFuture().get();
    }

    @AfterAll
    public void tearDown() {
        client.shutdown();
    }

    @Test
    public void testOperation() throws Exception {
        client.set("key", "value", 10000).toCompletableFuture().get();
        String value = client.get("key").toCompletableFuture().get();
        System.out.println(value);
    }
}
