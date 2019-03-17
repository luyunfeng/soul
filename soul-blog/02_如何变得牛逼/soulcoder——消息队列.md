[toc]
- [概述](#%E6%A6%82%E8%BF%B0)
- [为什么使用消息队列？](#%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A8%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97)
- [消息队列的缺点](#%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97%E7%9A%84%E7%BC%BA%E7%82%B9)
- [消息队列如何选型](#%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97%E5%A6%82%E4%BD%95%E9%80%89%E5%9E%8B)
  - [RabbitMQ](#rabbitmq)
  - [阿里云MNS](#%E9%98%BF%E9%87%8C%E4%BA%91mns)
  - [阿里云ONS / RocketMQ](#%E9%98%BF%E9%87%8C%E4%BA%91ons--rocketmq)
  - [Kafka](#kafka)
  - [产品总结](#%E4%BA%A7%E5%93%81%E6%80%BB%E7%BB%93)
- [如何保证消息队列的可用性](#%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97%E7%9A%84%E5%8F%AF%E7%94%A8%E6%80%A7)
- [如何保证消息不被重复消费(幂等性)](#%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E6%B6%88%E6%81%AF%E4%B8%8D%E8%A2%AB%E9%87%8D%E5%A4%8D%E6%B6%88%E8%B4%B9%E5%B9%82%E7%AD%89%E6%80%A7)
- [如何保证消费的可靠传输](#%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E6%B6%88%E8%B4%B9%E7%9A%84%E5%8F%AF%E9%9D%A0%E4%BC%A0%E8%BE%93)
  - [生产者丢数据](#%E7%94%9F%E4%BA%A7%E8%80%85%E4%B8%A2%E6%95%B0%E6%8D%AE)
  - [消息队列丢数据](#%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97%E4%B8%A2%E6%95%B0%E6%8D%AE)
  - [消费者丢数据](#%E6%B6%88%E8%B4%B9%E8%80%85%E4%B8%A2%E6%95%B0%E6%8D%AE)
  - [结论](#%E7%BB%93%E8%AE%BA)
    - [生产者丢数据](#%E7%94%9F%E4%BA%A7%E8%80%85%E4%B8%A2%E6%95%B0%E6%8D%AE-1)
    - [消息队列丢数据](#%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97%E4%B8%A2%E6%95%B0%E6%8D%AE-1)
    - [消费者丢数据](#%E6%B6%88%E8%B4%B9%E8%80%85%E4%B8%A2%E6%95%B0%E6%8D%AE-1)
- [如何保证消息的顺序性](#%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E6%B6%88%E6%81%AF%E7%9A%84%E9%A1%BA%E5%BA%8F%E6%80%A7)
- [Kafka](#kafka-1)
  - [名词解释](#%E5%90%8D%E8%AF%8D%E8%A7%A3%E9%87%8A)
  - [partition](#partition)
  - [在 zk 中存了什么](#%E5%9C%A8-zk-%E4%B8%AD%E5%AD%98%E4%BA%86%E4%BB%80%E4%B9%88)
  - [Kafka的API](#kafka%E7%9A%84api)
  - [Kakfa Broker Leader的选举](#kakfa-broker-leader%E7%9A%84%E9%80%89%E4%B8%BE)
    - [kafka常见的一些问题](#kafka%E5%B8%B8%E8%A7%81%E7%9A%84%E4%B8%80%E4%BA%9B%E9%97%AE%E9%A2%98)
      - [消息传输一致性语义](#%E6%B6%88%E6%81%AF%E4%BC%A0%E8%BE%93%E4%B8%80%E8%87%B4%E6%80%A7%E8%AF%AD%E4%B9%89)
      - [kafka 为什么这么快](#kafka-%E4%B8%BA%E4%BB%80%E4%B9%88%E8%BF%99%E4%B9%88%E5%BF%AB)
      - [怎么保证发送消息不丢失](#%E6%80%8E%E4%B9%88%E4%BF%9D%E8%AF%81%E5%8F%91%E9%80%81%E6%B6%88%E6%81%AF%E4%B8%8D%E4%B8%A2%E5%A4%B1)
  - [kafka 参考文献](#kafka-%E5%8F%82%E8%80%83%E6%96%87%E7%8C%AE)
# 概述
![2019317131343](http://po6i7clbi.bkt.clouddn.com/2019317131343.png)
分析一个消息队列主要从这几个点出来。
在后半部分主要分析了 kafka 对以上几点的保证。
# 为什么使用消息队列？
![image.png](https://upload-images.jianshu.io/upload_images/4031250-78958ef5cbb472f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 消息队列的缺点
![201931713116](http://po6i7clbi.bkt.clouddn.com/201931713116.png)
# 消息队列如何选型
![2019317131255](http://po6i7clbi.bkt.clouddn.com/2019317131255.png)
## RabbitMQ
>[RabbitMQ](http://www.rabbitmq.com/) 

## 阿里云MNS
*   [MNS产品介绍](https://www.aliyun.com/product/mns)

## 阿里云ONS / RocketMQ
*   [ONS产品介绍](https://www.aliyun.com/product/ons)
*   [ONS开源社区对应产品——RocketMQ](https://github.com/alibaba/RocketMQ)

## Kafka
详见下文分析重点分析。

## 产品总结
事务支持方面，ONS/RocketMQ较为优秀，但是不支持消息批量操作, 不保证消息至少被消费一次.

Kafka提供完全分布式架构, 并有replica机制, 拥有较高的可用性和可靠性, 理论上支持消息无限堆积, 支持批量操作, 消费者采用Pull方式获取消息, 消息有序, 通过控制能够保证所有消息被消费且仅被消费一次. 但是官方提供的运维工具不友好，开源社区的运维工具支持的版本一般落后于最新版本的Kafka.

目前使用的MNS服务，拥有HTTP REST API, 使用简单, 数据可靠性高, 但是不保证消息有序，不能回溯数据.

RabbitMQ为重量级消息系统， 支持多协议(很多协议是目前业务用不到的), 但是不支持回溯数据, master挂掉之后， 需要手动从slave恢复, 可用性略逊一筹.

# 如何保证消息队列的可用性

以rcoketMQ为例，他的集群就有
* 多master 模式
* 多master多slave异步复制模式
* 多 master多slave同步双写模式 
![多master多slave模式部署架构图](https://upload-images.jianshu.io/upload_images/4031250-5636efd965570010.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第一眼看到这个图，就觉得和kafka好像，只是NameServer集群，在kafka中是用zookeeper代替，都是用来保存和发现master和slave用的。

通信过程如下:

Producer 与 NameServer集群中的其中一个节点（随机选择）建立长连接，定期从 NameServer 获取 Topic 路由信息，并向提供 Topic 服务的 Broker Master 建立长连接，且定时向 Broker 发送心跳。

Producer 只能将消息发送到 Broker master，但是 Consumer 则不一样，它同时和提供 Topic 服务的 Master 和 Slave建立长连接，既可以从 Broker Master 订阅消息，也可以从 Broker Slave 订阅消息。

那么kafka呢？
为了对比说明直接上kafka的拓补架构图
![image.png](https://upload-images.jianshu.io/upload_images/4031250-977d28f87407d339.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如上图所示，一个典型的Kafka集群中包含若干Producer（可以是web前端产生的Page View，或者是服务器日志，系统CPU、Memory等），若干broker（Kafka支持水平扩展，一般broker数量越多，集群吞吐率越高），若干Consumer Group，以及一个Zookeeper集群。Kafka通过Zookeeper管理集群配置，选举leader，以及在Consumer Group发生变化时进行rebalance。Producer使用push模式将消息发布到broker，Consumer使用pull模式从broker订阅并消费消息。

# 如何保证消息不被重复消费(幂等性)

最骚的一个操作，消费者业务自己去保证幂等性。

换一个说法，如何保证消息队列的幂等性?

另外说一点，幂等性的保证需要在一次请求中所有链路都是幂等的，再能最终保证这次请求的幂等，比如前段按钮点击两次，后端认为都是这是两次不同的请求，当然处理成两次请求，所以说一个请求的幂等性，需要全局的幂等才能保证。

其实无论是哪种消息队列，造成重复消费原因其实都是类似的。正常情况下，消费者在消费消息时候，消费完毕后，会发送一个确认信息给消息队列，消息队列就知道该消息被消费了，就会将该消息从消息队列中删除。只是不同的消息队列发送的确认信息形式不同。

例如RabbitMQ是发送一个ACK确认消息，RocketMQ是返回一个CONSUME_SUCCESS成功标志，kafka实际上有个offset的概念，简单说一下(后续详细解释),就是每一个消息都有一个offset，kafka消费过消息后，需要提交offset，让消息队列知道自己已经消费过了。

那造成重复消费的原因?，就是因为网络传输等等故障，确认信息没有传送到消息队列，导致消息队列不知道自己已经消费过该消息了，再次将该消息分发给其他的消费者。

如何解决?这个问题针对业务场景来答分以下几点

* (1)你拿到这个消息做数据库的insert操作。那就容易了，给这个消息做一个唯一主键，那么就算出现重复消费的情况，就会导致主键冲突，避免数据库出现脏数据。

* (2)你拿到这个消息做redis的set的操作，那就容易了，不用解决，因为你无论set几次结果都是一样的，set操作本来就算幂等操作。

* (3)如果上面两种情况还不行，上大招。准备一个第三方介质,来做消费记录。以redis为例，给消息分配一个全局id，只要消费过该消息，将<id,message>以K-V形式写入redis。那消费者开始消费前，先去redis中查询有没消费记录即可。还有一个方法，采用 setnx 的方案。

# 如何保证消费的可靠传输

其实这个可靠性传输，每种MQ都要从三个角度来分析:生产者弄丢数据、消息队列弄丢数据、消费者弄丢数据。

## 生产者丢数据
从生产者弄丢数据这个角度来看，RabbitMQ提供transaction和confirm模式来确保生产者不丢消息。
transaction(事物机制)机制就是说，发送消息前，开启事物(channel.txSelect())，然后发送消息，如果发送过程中出现什么异常，事物就会回滚(channel.txRollback())，如果发送成功则提交事物(channel.txCommit())。然而缺点就是吞吐量下降了。

生产上用confirm模式的居多。一旦channel进入confirm模式，所有在该信道上面发布的消息都将会被指派一个唯一的ID(从1开始)，一旦消息被投递到所有匹配的队列之后，rabbitMQ就会发送一个Ack给生产者(包含消息的唯一ID)，这就使得生产者知道消息已经正确到达目的队列了.如果rabiitMQ没能处理该消息，则会发送一个Nack消息给你，你可以进行重试操作。

简单来讲 confirm模式就是生产者发送请求，到了消息队列，消息队列会回复一个消息收到的应答，如果没收到，生产者开始重试。

## 消息队列丢数据
处理消息队列丢数据的情况，一般是开启持久化磁盘的配置。这个持久化配置可以和confirm机制配合使用，你可以在消息持久化磁盘后，再给生产者发送一个Ack信号。这样，如果消息持久化磁盘之前，rabbitMQ阵亡了，那么生产者收不到Ack信号，生产者会自动重发。

## 消费者丢数据
消费者丢数据一般是因为采用了自动确认消息模式。这种模式下，消费者会自动确认收到信息。这时rahbitMQ会立即将消息删除，这种情况下如果消费者出现异常而没能处理该消息(但是消息队列那边已经认为消息被消费了)，就会丢失该消息。

至于解决方案，采用手动确认消息即可。

kafka为例
![kafka Replication的数据流向图](https://upload-images.jianshu.io/upload_images/4031250-7fa802db42ee197c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Producer在发布消息到某个Partition时，先通过ZooKeeper找到该Partition的Leader，然后无论该Topic的Replication Factor为多少（也即该Partition有多少个Replica），Producer只将该消息发送到该Partition的Leader。Leader会将该消息写入其本地Log。每个Follower都从Leader中pull数据。

## 结论

### 生产者丢数据
在kafka生产中，基本都有一个leader和多个follwer。follwer会去同步leader的信息。因此，为了避免生产者丢数据，做如下两点配置

1. 第一个配置要在producer端设置acks=all。这个配置保证了，follwer同步完成后，才认为消息发送成功。

2. 在producer端设置retries=MAX，一旦写入失败，这无限重试

### 消息队列丢数据
针对消息队列丢数据的情况，无外乎就是，数据还没同步，leader就挂了，这时zookpeer会将其他的follwer切换为leader,那数据就丢失了。针对这种情况，应该做两个配置。

1. replication.factor参数，这个值必须大于1，即要求每个partition必须有至少2个副本。

2. min.insync.replicas参数，这个值必须大于1，这个是要求一个leader至少感知到有至少一个follower还跟自己保持联系这两个配置加上上面生产者的配置联合起来用，基本可确保kafka不丢数据

### 消费者丢数据

这种情况一般是自动提交了offset，然后你处理程序过程中挂了。kafka以为你处理好了。再强调一次offset是干嘛的。

offset：指的是kafka的topic中的每个消费组消费的下标。简单的来说就是一条消息对应一个offset下标，每次消费数据的时候如果提交offset，那么下次消费就会从提交的offset加一那里开始消费。

比如一个topic中有100条数据，我消费了50条并且提交了，那么此时的kafka服务端记录提交的offset就是49(offset从0开始)，那么下次消费的时候offset就从50开始消费。


# 如何保证消息的顺序性

针对这个问题，通过某种算法，将需要保持先后顺序的消息放到同一个消息队列中(kafka中就是partition,rabbitMq中就是queue)。然后只用一个消费者去消费该队列。

有的人会问:那如果为了吞吐量，有多个消费者去消费怎么办？

简单来说消息的时序性也可以通过错误重试来解决。

比如我们有一个微博的操作，发微博、写评论、删除微博，这三个异步操作。如果是这样一个业务场景，那只要重试就行。比如你一个消费者先执行了写评论的操作，但是这时候，微博都还没发，写评论一定是失败的，等一段时间。等另一个消费者，先执行写评论的操作后，再执行，就可以成功。

总之，针对这个问题，我的观点是保证入队有序就行，出队以后的顺序交给消费者自己去保证，没有固定套路。

# Kafka 
## 名词解释
1. producer：消息生产者，发布消息到 kafka 集群的终端或服务。
2. broker：kafka 集群中包含的服务器。 
3. topic：每条发布到 kafka 集群的消息属于的类别，即 kafka 是面向 topic 的。
4. partition： 是物理上的概念，每个 topic 包含一个或多个 partition。kafka 分配的单位是 partition。
5. consumer：从 kafka 集群中消费消息的终端或服务。
6. Consumer group：high-level consumer API 中，每个 consumer 都属于一个 consumer group，每条消息只能被 consumer group 中的一个 Consumer 消费，但可以被多个 consumer group 消费。
7. replica：partition 的副本，保障 partition 的高可用。
8. leader：replica 中的一个角色， producer 和 consumer 只跟 leader 交互。
9. follower：replica 中的一个角色，从 leader 中复制数据。
10. controller：kafka 集群中的其中一个服务器，用来进行 leader election 以及 各种 failover。
12. zookeeper：kafka 通过 zookeeper 来存储集群的 meta 信息。


## partition
为了做到水平扩展，一个topic实际是由多个partition组成的，遇到瓶颈时，可以通过增加partition的数量来进行横向扩容。
单个parition内是保证消息有序。

1. 一个Topic的Partition数量大于等于Broker的数量，可以提高吞吐率。
2. 同一个Partition的Replica尽量分散到不同的机器，高可用。
## 在 zk 中存了什么
![2019317192730](http://po6i7clbi.bkt.clouddn.com/2019317192730.png)
## Kafka的API
kafka api 提供了很多功能比如

生产者能指定 topic 和 Partition 来投递消息，并且还有延迟消息，事务消息等等，详见下面的 api 文档
http://kafka.apache.org/documentation.html#api

这个是 api 的中文文档
http://orchome.com/66

## Kakfa Broker Leader的选举
Kakfa Broker集群受Zookeeper管理。

所有的Kafka Broker节点一起去Zookeeper上注册一个临时节点，并且只有一个Kafka Broker会注册成功，其他的都会失败，所以这个成功在Zookeeper上注册临时节点的这个Kafka Broker会成为```Kafka Broker Controller```，其他的Kafka broker叫```Kafka Broker follower```。（这个过程叫Controller在ZooKeeper注册Watch）。

这个Controller会监听其他的Kafka Broker的所有信息，如果这个kafka broker controller宕机了，在zookeeper上面的那个临时节点就会消失，此时所有的kafka broker又会一起去Zookeeper上注册一个临时节点。

### kafka常见的一些问题
#### 消息传输一致性语义
Kafka提供3种消息传输一致性语义：最多1次，最少1次，恰好1次。

最少1次(at most once)：可能会重传数据，有可能出现数据被重复处理的情况;

最多1次(at least once)：可能会出现数据丢失情况;

恰好1次(Exactly once)：并不是指真正只传输1次，只不过有一个机制。确保不会出现“数据被重复处理”和“数据丢失”的情况。

####  kafka 为什么这么快
1. 生产者写入：页缓存技术 + 磁盘顺序写 

操作系统本身有一层缓存，叫做page cache，是在内存里的缓存，我们也可以称之为os cache，意思就是操作系统自己管理的缓存。
每新写一条消息，kafka就是在对应的文件append写，所以性能非常高。

2. 消费者读取：零 copy 技术

https://mp.weixin.qq.com/s/sCRC5h0uw2DWD2MixI6pZw

#### 怎么保证发送消息不丢失
我觉得的靠的是这两个参数
```
#acks
props.put("acks", "all")
//0：不进行消息接收确认，即Client端发送完成后不会等待Broker的确认
//1：由Leader确认，Leader接收到消息后会立即返回确认信息
//all：集群完整确认，Leader会等待所有in-sync的follower节点都确认收到消息后，再返回确认信息

这点也就保证了所有节点都都有一个副本，及时leader 宕机后主节点也不会
#request.required.acks
# 0：不保证消息的到达确认，只管发送，低延迟但是会出现消息的丢失，在某个server失败的情况下，有点像TCP
# 1：发送消息，并会等待leader 收到确认后，一定的可靠性
# -1：发送消息，等待leader收到确认，并进行复制操作后，才返回，最高的可靠性
并且在落后太多会把落后的节点剔除。
```

## kafka 参考文献

这篇主要从生产和消费的角度详细给出的过程
https://www.cnblogs.com/cyfonly/p/5954614.html
