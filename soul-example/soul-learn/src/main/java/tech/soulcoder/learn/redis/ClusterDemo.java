package tech.soulcoder.learn.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yunfeng.lu
 * @create 2019/5/8.
 */
public class ClusterDemo {

    public static void main(String[] args) {
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<>();
        // 添加节点
        hostAndPortsSet.add(new HostAndPort("redis", 7000));
        hostAndPortsSet.add(new HostAndPort("redis", 7001));
        hostAndPortsSet.add(new HostAndPort("redis", 7002));
        hostAndPortsSet.add(new HostAndPort("redis", 7003));
        hostAndPortsSet.add(new HostAndPort("redis", 7004));
        hostAndPortsSet.add(new HostAndPort("redis", 7005));
        // connectionTimeOut：建立链接的超时时间
        // soTimeOut(相当于socketTimeOue)：等待服务器返回数据的超时时间
        JedisCluster jedisCluster = new JedisCluster(hostAndPortsSet,
            20000, 20000, 10, new JedisPoolConfig());
        for (int i = 0; i < 1000000; i++) {
            jedisCluster.set("a" + i, "test" + i);
        }

    }
}
