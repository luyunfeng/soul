package tech.soulcoder.thift.price;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import tech.soulcoder.thrift.demo.api.RPCDateService;

/**
 * @author yunfeng.lu
 * @create 2019/5/12.
 */
public class RPCThriftClient {
    private RPCDateService.Client client;
    private TBinaryProtocol protocol;
    private TSocket transport;
    private String host;
    private int port;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void init() {
        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport);
        client = new RPCDateService.Client(protocol);
    }

    public RPCDateService.Client getRPCThriftService() {
        return client;
    }

    public void open() throws TTransportException {
        transport.open();
    }

    public void close() {
        transport.close();
    }
}

