package tech.soulcoder.thift.price;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yunfeng.lu
 * @create 2019/5/12.
 */
@Configuration
public class RPCThriftClientConfig {
    @Value("${thrift.host}")
    private String host;
    @Value("${thrift.port}")
    private int port;

    @Bean(initMethod = "init")
    public RPCThriftClient rpcThriftClient() {
        RPCThriftClient rpcThriftClient = new RPCThriftClient();
        rpcThriftClient.setHost(host);
        rpcThriftClient.setPort(port);
        return rpcThriftClient;
    }
}
