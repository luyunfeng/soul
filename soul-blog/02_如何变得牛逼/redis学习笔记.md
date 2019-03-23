# 概述
主要从下面几个方面去了解 redis
* 基本命令
* 持久化机制
* 分布式机制
* 分片原理(客户端分片，代理层分片，服务端分片(集群))
* 客户端工具的使用
```
【redis主从】：
是备份关系， 我们操作主库，数据也会同步到从库。 如果主库机器坏了，从库可以上。就好比你 D盘的片丢了，但是你移动硬盘里边备份有。

【redis哨兵】：
哨兵保证的是HA，保证特殊情况故障自动切换，哨兵盯着你的“redis主从集群”，如果主库死了，它会告诉你新的老大是谁。

【redis集群】：
集群保证的是高并发，因为多了一些兄弟帮忙一起扛。同时集群会导致数据的分散，整个redis集群会分成一堆数据槽，即不同的key会放到不不同的槽中。
```


# redis主从

# redis哨兵sentinel

# redis集群cluster

# redisson 
https://redisson.org/

# 参考文献
https://www.cnblogs.com/demingblog/p/10295236.html
redis 官方文档
http://www.redis.cn/documentation.html
