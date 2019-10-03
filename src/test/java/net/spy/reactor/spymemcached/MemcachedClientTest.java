package net.spy.reactor.spymemcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.Test;


/**
 * memcached client test
 *
 * @author linux_chiina
 */
public class MemcachedClientTest {
    @Test
    public void testOperations() throws Exception {
        MemcachedClient mc = new MemcachedClient(
                new ConnectionFactoryBuilder()
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                        .build(),
                AddrUtil.getAddresses("127.0.0.1:11211"));

        ReactiveMemcachedClient rmc = new ReactiveMemcachedClient(mc);
        rmc.set("nick", 0, "linux_china")
                .then(rmc.get("nick"))
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }
}
