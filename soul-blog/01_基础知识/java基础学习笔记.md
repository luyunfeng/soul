[toc]
- [String、StringBufer、StringBuilder](#stringstringbuferstringbuilder)
  - [字符串的内部数组](#%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%9A%84%E5%86%85%E9%83%A8%E6%95%B0%E7%BB%84)
  - [字符串拼接](#%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%8B%BC%E6%8E%A5)
  - [字符串缓存、内存](#%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%BC%93%E5%AD%98%E5%86%85%E5%AD%98)
  - [为什么 String 设计成不可变的](#%E4%B8%BA%E4%BB%80%E4%B9%88-string-%E8%AE%BE%E8%AE%A1%E6%88%90%E4%B8%8D%E5%8F%AF%E5%8F%98%E7%9A%84)
- [动态代理](#%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86)
  - [静态代理](#%E9%9D%99%E6%80%81%E4%BB%A3%E7%90%86)
  - [动态代理](#%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86-1)
- [基本数据类型和引用数据类型](#%E5%9F%BA%E6%9C%AC%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B%E5%92%8C%E5%BC%95%E7%94%A8%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B)
  - [缓存机制](#%E7%BC%93%E5%AD%98%E6%9C%BA%E5%88%B6)
  - [自动拆装箱的注意点](#%E8%87%AA%E5%8A%A8%E6%8B%86%E8%A3%85%E7%AE%B1%E7%9A%84%E6%B3%A8%E6%84%8F%E7%82%B9)
  - [Integer 内存结构组成](#integer-%E5%86%85%E5%AD%98%E7%BB%93%E6%9E%84%E7%BB%84%E6%88%90)
- [集合](#%E9%9B%86%E5%90%88)
- [Map](#map)
  - [LinkedHashMap](#linkedhashmap)
  - [TreeMap](#treemap)
  - [HashMap](#hashmap)
    - [HashMap内部实现基本点分析](#hashmap%E5%86%85%E9%83%A8%E5%AE%9E%E7%8E%B0%E5%9F%BA%E6%9C%AC%E7%82%B9%E5%88%86%E6%9E%90)
      - [resize()](#resize)
    - [容量（capcity）和负载系数（load factor）](#%E5%AE%B9%E9%87%8Fcapcity%E5%92%8C%E8%B4%9F%E8%BD%BD%E7%B3%BB%E6%95%B0load-factor)
    - [树化](#%E6%A0%91%E5%8C%96)
    - [【补充】如何解决 hash 冲突](#%E8%A1%A5%E5%85%85%E5%A6%82%E4%BD%95%E8%A7%A3%E5%86%B3-hash-%E5%86%B2%E7%AA%81)
- [线程安全的集合类（JUC）](#%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E7%9A%84%E9%9B%86%E5%90%88%E7%B1%BBjuc)
  - [ConcurrentHashMap](#concurrenthashmap)
- [IO](#io)
  - [基本概念](#%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5)
    - [操作系统一般分为```内核空间```和```用户空间```](#%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F%E4%B8%80%E8%88%AC%E5%88%86%E4%B8%BA%E5%86%85%E6%A0%B8%E7%A9%BA%E9%97%B4%E5%92%8C%E7%94%A8%E6%88%B7%E7%A9%BA%E9%97%B4)
    - [IO的过程](#io%E7%9A%84%E8%BF%87%E7%A8%8B)
    - [零 copy 技术](#%E9%9B%B6-copy-%E6%8A%80%E6%9C%AF)
    - [同步 & 异步](#%E5%90%8C%E6%AD%A5--%E5%BC%82%E6%AD%A5)
    - [阻塞 & 非阻塞](#%E9%98%BB%E5%A1%9E--%E9%9D%9E%E9%98%BB%E5%A1%9E)
  - [概念简述](#%E6%A6%82%E5%BF%B5%E7%AE%80%E8%BF%B0)
  - [java 的 NIO](#java-%E7%9A%84-nio)
  - [参考](#%E5%8F%82%E8%80%83)

# String、StringBufer、StringBuilder

> String 它是典型的Immutable(不可改变)类，被声明成为fnal class，所有属性也都是fnal的。也由于它的不可变性，类似拼接、裁剪字符串等动作，都会产生新的String对象。在循环场景下不建议使用这些功能。

> StringBufer是为解决上面提到拼接产生太多中间对象的问题而提供的一个类，可以用append或者add方法，把字符串添加到已有序列的末尾或者指定位置。StringBufer本质是一个线程安全的可修改字符序列，它保证了线程安全(***简单粗暴各种修改数据的方法都加上synchronized关键字实现的***)，也随之带来了额外的性能开销，所以除非有线程安全的需要，不然还是推荐使用它的后继者，也就是StringBuilder。

> StringBuilder 在能力上和StringBufer没有本质区别，但是它去掉了线程安全的部分，有效减小了开销，是绝大部分情况下进行字符串拼接的首选。从 builder 命名来看这个类专门就是为创建而生。

* Immutable对象在拷贝时不需要额外复制数据。
## 字符串的内部数组
* 为了实现修改字符序列的目的，StringBufer和StringBuilder底层都是利用可修改的char数组(JDK 9以后是byte)，都继承了AbstractStringBuilder。

* 这个内部数组应该创建成多大的呢？如果太小，拼接的时候可能要重新创建足够大的数组；如果太大，又会浪费空间。目前的实现是，构建时初始字符串长度加16（这意味着，如果没有构建对象时输入最初的字符串，那么初始值就是16）。我们如果确定拼接会发生非常多次，而且大概是可预计的，那么就可以指定合适的大小，避免很多次扩容的开销。扩容会产生多重开销，因为要抛弃原有数组，创建新的（可以简单认为是倍数）数组，还要进行arraycopy。

## 字符串拼接
* 如果在代码拼接说 String 拼接效率低下，但是在 jdk8即使你使用 String 拼接编译器还是会把你优化成 StringBuilder，jdk9优化成StringConcatFactory(暂时没用了解过)

## 字符串缓存、内存
* String在jdk6以后提供了intern()方法，目的是提示JVM把相应字符串缓存起来，以备重复使用。但是不建议使用。
* jdk6被缓存的字符串是存在所谓PermGen里的，也就是臭名昭著的“永久代”，这个空间是很有限的，也基本不会被FullGC之外的垃圾收集照顾到。所以，如果使用不当，OOM就会光顾。

* jdk7存放在堆中。

* jdk8出现了MetaSpace(元数据区),就存在元空间中。

* intern是一种显式地排重机制，但是它也有一定的副作用，因为需要开发者写代码时明确调用，一是不方便，每一个都显式调用是非常麻烦的；另外就是我们很难保证效率，应用开发阶段很难清楚地预计字符串的重复情况，有人认为这是一种污染代码的实践。
幸好在Oracle JDK 8u20之后，推出了一个新的特性，也就是G1 GC下的字符串排重。它是通过将相同数据的字符串指向同一份数据来做到的，是JVM底层的改变，并不需要Java类库做什么修改。
开启 G1 GC 后可加入这个参数开启```-XX:+UseStringDeduplication```

## 为什么 String 设计成不可变的
* 字符串常量池的需要
> 因为 String 在一个系统中会被大量使用，所以采用了池化技术，也就是上面说的字符串缓存，如果设计成可变的话，两个不同的地方使用了缓存中同一个字符串对象，这时候期中一个地方对字符串进行了修改，另外一个地方也会跟着被改变。

* 允许String对象缓存HashCode
> Java中String对象的哈希码被频繁地使用, 比如在hashMap 等容器中。
字符串不变性保证了hash码的唯一性,因此可以放心地进行缓存.这也是一种性能优化手段,意味着不必每次都去计算新的哈希码. 在String类的定义中有如下代码:
```
private int hash;//用来缓存HashCode
```
* 安全性
> String 被当成很对方法的入参，如果在另外一个方法里面被修改，
破坏了本意，设计成不可变，开发者就不必担心这个问题了。

* 不可变对象天生就是线程安全的

> 因为不可变对象不能被改变，所以他们可以自由地在多个线程之间共享。不需要任何同步处理。


# 动态代理
 [深入理解RPC之动态代理篇](http://cmsblogs.com/?p=3873)这篇文章针对集中动态代理有比较详细的分析
动态代理的应用场景比如：RPC、安全、日志、事务

## 静态代理
事先写好代理类，可以手工编写，也可以用工具生成。缺点是每个业务类都要对应一个代理类，非常不灵活。

## 动态代理
运行时自动生成代理对象。缺点是生成代理代理对象和调用代理方法都要额外花费时间。
> JDK动态代理：基于Java反射机制实现，必须要实现了接口的业务类才能用这种办法生成代理对象。新版本也开始结合ASM机制。

 > cglib动态代理：基于ASM机制实现，通过生成业务类的子类作为代理类。

# 基本数据类型和引用数据类型
## 缓存机制
缓存机制并不是只有Integer才有，同样存在于其他的一些包装类，比如：
* Boolean，缓存了true/ false对应实例，确切说，只会返回两个常量实例Boolean.TRUE/ FALSE。
* Short，同样是缓存了-128到127之间的数值。
* Byte，数值有限，所以全部都被缓存。
* Character，缓存范围'\ u0000' 到 '\ u007F'。
继续深挖缓存，I nteger的缓存范围虽然默认是-128到127，但是在特别的应用场景，比如我们明确知道应用会频繁使用更大的数值，这时候应该怎么办呢？
```
-XX:AutoBoxCacheMax=N
```
缓存上限值实际是可以根据需要调整的，JVM提供了参数设置：
## 自动拆装箱的注意点
>  建议避免无意中的装箱、拆箱行为，尤其是在性能敏感的场合，创建10万个Java对象和10万个整数的开销可不是一个数量级的，不管是内存使用还是处理速度，光是对象头的空间占用就已经是数量级的差距了。

## Integer 内存结构组成
1. Mark Word: 标记位 4字节，类似轻量级锁标记位，偏向锁标记位等。
2. Class对象指针: 4字节，指向对象对应class对象的内存地址。
3. 对象实际数据:对象所有成员变量。
4. 对齐:对齐填充字节，按照8个字节填充。
Integer占用内存大小，4+ 4+ 4+ 4= 16字节。

# 集合
![狭义的集合框架](https://upload-images.jianshu.io/upload_images/4031250-738e1cc39abefde6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# Map
![Map](https://upload-images.jianshu.io/upload_images/4031250-525d7ec843daab26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## LinkedHashMap
LinkedHashMap通常提供的是遍历顺序符合插入顺序，它的实现是通过为条目（键值对）维护一个双向链表。注意，通过特定构造函数，我们可以创建反映访问顺序的实例，所谓的put、get、compute等都算作“访问”。设置大小只能最多 N 个元素，有点类似于 LUR，但是他是基于访问的次数来确定的。

## TreeMap
它的整体顺序是由键的顺序关系决定的，通过Comparator或Comparable（自然顺序）来决定。

## HashMap

### HashMap内部实现基本点分析

数组（Node[ ]  table）和链表结合组成的复合结构

HashMap是一个lazy-load原则，就是在创建对象的时候不会去初始化空间大小，只有在第一个元素 put 进去的时候这个时候才调用``` resize()```方法

#### resize() 
* 门限值等于（负载因子）x（容量），如果构建HashMap的时候没有指定它们，那么就是依据相应的默认常量值。
* 门限通常是以倍数进行调整 （newThr =  oldThr < <  1），我前面提到，根据putVal中的逻辑，当元素个数超过门限大小时，则调整Map大小。
* 扩容后，需要将老的数组中的元素重新放置到新的数组，这是扩容的一个主要开销来源。


### 容量（capcity）和负载系数（load factor）

* 如果没有特别需求，不要轻易进行更改，因为JDK自身的默认负载因子是非常符合通用场景的需求的。

*  如果确实需要调整，建议不要设置超过0.75的数值，因为会显著增加冲突，降低HashMap的性能。

* 如果使用太小的负载因子，按照上面的公式，预设容量值也进行调整，否则可能会导致更加频繁的扩容，增加无谓的开销，本身访问性能也会受影响

### 树化
![image.png](https://upload-images.jianshu.io/upload_images/4031250-dac2fbba11f3e82d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
上面是精简过的treeifyBin示意，综合这两个方法，树化改造的逻辑就非常清晰了，可以理解为，当bin的数量大于TREEI FY_THRESHOLD时：

* 如果容量小于MIN_TREEIFY_CAPACI TY，只会进行简单的扩容
* 如果容量大于MIN_TREEIFY_CAPACI TY ，则会进行树化改造

本质上这是个安全问题。因为在元素放置过程中，
如果一个对象哈希冲突，都被放置到同一个桶里，则会形成一个链表，
我们知道链表查询是线性的，会严重影响存取的性能。

而在现实世界，构造哈希冲突的数据并不是非常复杂的事情，恶意代码就可以利用这些数据大量与服务器端交互，导致服务器端CPU大量占用，这就构成了哈希碰撞拒绝服务攻击，国内一线互联网公司就发生过类似攻击事件。

### 【补充】如何解决 hash 冲突 

> ```开放定址法```基本思想是：当关键字key的哈希地址p= H（key）出现冲突时，以p为基础，产生另一个哈希地址p1，如果p1仍然冲突，再以p为基础，产生另一个哈希地址p2，…，直到找出一个不冲突的哈希地址pi ，将相应元素存入其中。

> ```再哈希法```这种方法是同时构造多个不同的哈希函数：Hi= RH1（key）  i= 1，2，…，k 当哈希地址Hi= RH1（key）发生冲突时，再计算Hi= RH2（key）… …，直到冲突不再产生。这种方法不易产生聚集，但增加了计算时间。

> ```链地址法```这种方法的基本思想是将所有哈希地址为i的元素构成一个称为同义词链的单链表，并将单链表的头指针存在哈希表的第i个单元中，因而查找、插入和删除主要在同义词链中进行。链地址法适用于经常进行插入和删除的情况。

> ```建立公共溢出区```这种方法的基本思想是：将哈希表分为基本表和溢出表两部分，凡是和基本表发生冲突的元素，一律填入溢出表。

# 线程安全的集合类（JUC）
## ConcurrentHashMap
这个是1.7 的数据结构
![2019326124649](http://po6i7clbi.bkt.clouddn.com/2019326124649.png)

![2019326124814](http://po6i7clbi.bkt.clouddn.com/2019326124814.png)

在1.8版本以前，ConcurrentHashMap采用分段锁的概念，使锁更加细化，但是1.8已经改变了这种思路，而是利用CAS+Synchronized来保证并发更新的安全，当然底层采用数组+链表+红黑树的存储结构。

* [详细分析的博客](https://www.jianshu.com/p/e694f1e868ec)

* [1.8 源码解析](http://cmsblogs.com/?p=2283)

> 重要概念

* table：用来存放Node节点数据的，默认为null，默认大小为16的数组，每次扩容时大小总是2的幂次方；

* nextTable：扩容时新生成的数据，数组为table的两倍；

* Node：节点，保存key-value的数据结构；

* ForwardingNode：一个特殊的Node节点，hash值为-1，其中存储nextTable的引用。只有table发生扩容的时候，ForwardingNode才会发挥作用，作为一个占位符放在table中表示当前节点为null或则已经被移动

* sizeCtl：控制标识符，用来控制table初始化和扩容操作的，在不同的地方有不同的用途，其值也不同，所代表的含义也不同
```
负数代表正在进行初始化或扩容操作
-1代表正在初始化
-N 表示有N-1个线程正在进行扩容操作
正数或0代表hash表还没有被初始化，这个数值表示初始化或下一次进行扩容的大小
```

> 重要内部类

* Node
作为ConcurrentHashMap中最核心、最重要的内部类，Node担负着重要角色：key-value键值对。所有插入ConCurrentHashMap的中数据都将会包装在Node中

区别去普通的HashMap 他的 Node 很多字段都是采用了volatile
```
volatile V val; //带有volatile，保证可见性
volatile Node<K,V> next;//下一个节点的指针
```
> TreeNode

在ConcurrentHashMap中，如果链表的数据过长是会转换为红黑树来处理。当它并不是直接转换，而是将这些链表的节点包装成TreeNode放在TreeBin对象中，然后由TreeBin完成红黑树的转换。

> TreeBin

该类并不负责key-value的键值对包装，它用于在链表转换为红黑树时包装TreeNode节点，也就是说ConcurrentHashMap红黑树存放是TreeBin，不是TreeNode。该类封装了一系列的方法，包括putTreeVal、lookRoot、UNlookRoot、remove、balanceInsetion、balanceDeletion。


> ConcurrentHashMap的初始化

ConcurrentHashMap的初始化主要由initTable()方法实现，在上面的构造函数中我们可以看到，其实ConcurrentHashMap在构造函数中并没有做什么事，仅仅只是设置了一些参数而已。其真正的初始化是发生在插入的时候，例如put、merge、compute、computeIfAbsent、computeIfPresent操作时。





# IO

## 基本概念

### 操作系统一般分为```内核空间```和```用户空间```

### IO的过程

IO一般有2个阶段：（1）数据准备阶段，磁盘文件读入到```内核空间```。（2）数据拷贝阶段，内核空间拷贝到```用户空间```。

### 零 copy 技术
在 IO 的过程有两个数据准备阶段和数据拷贝阶段，之后如果需要在利用网络传输，这时候又要拷贝到内核空间，这时候才能通过 Socket 发送，零 copy 就是去掉了 copy 到用户空间这个过程，直接在内核之间完成数据的 copy。
[kafka 的设计就有这个技术](https://mp.weixin.qq.com/s/sCRC5h0uw2DWD2MixI6pZw)

### 同步 & 异步

IO数据拷贝阶段若是由```用户程序```负责，那么就是```同步```。
IO数据拷贝阶段若是由```操作系统```负责，那么就是```异步```。

### 阻塞 & 非阻塞

用户程序在IO```数据准备阶段```调用read()方法后，若内核未直接返回结果(无法从事其他任务，只有当条件就绪才能继续)，那么就是```阻塞```；若直接返回结果(不管成功还是失败)，则是```非阻塞```。

## 概念简述

（1）同步阻塞IO（Blocking IO）：即用户程序调用read()方法后，在IO过程的两个阶段中，用户程序一直阻塞。

（2）同步非阻塞IO（Non-blocking IO）：默认创建的socket都是阻塞的，非阻塞IO要求socket被设置为NONBLOCK。注意这里所说的NIO并非[Java](http://lib.csdn.net/base/17 "Java EE知识库")的NIO（New IO）库。
```IO第一阶段```，用户程序发出read请求后，```应用程序```轮询查询内核数据是否准备好，该线程是阻塞的，持续占用CPU时间，但是会直接返回给用户程序结果，让其他线程可以做其他事情，故是```非阻塞```。待到内核数据准备好之后，```IO的第二阶段```，应用程序再次调用read方法复制数据，此阶段```block（阻塞）```,由应用程序负责处理。故```同步```。


（3）IO多路复用（IO Multiplexing）：即经典的Reactor设计模式，有时也称为异步阻塞IO，Java中的Selector和Linux中的epoll都是这种模型。
在内核中轮询数据是否准备好，select或者poll也是阻塞方法，当数据准备好之后，会通知应用程序进行IO第二阶段的数据拷贝。这个是JAVA NIO的实现原理，本质上也是异步阻塞模型

（4）异步IO（Asynchronous IO）：即经典的Proactor设计模式，也称为异步非阻塞IO。
操作系统采用了epoll进行处理请求，故不必轮询IO连接。内核空间到用户空间的拷贝，是操作系统独立完成的，故是异步非阻塞。

## java 的 NIO

> Bufer，高效的数据容器，除了布尔类型，所有原始数据类型都有相应的Bufer实现

> Channel，类似在Linux之类操作系统上看到的文件描述符，是NI O中被用来支持批量式I O操作的一种抽象

> Selector，是NI O实现多路复用的基础，它提供了一种高效的机制，可以检测到注册在Selector上的多个Channel中，是否有Channel处于就绪状态，进而实现了单线程对多Channel的高效管理。 然后 linux 和 windows 系统的实现是不一样的
linux 就是典型的 epoll ，windows 系统采用的是iocp

## 参考 

[Linux 内核空间与用户空间实现与分析](https://www.jb51.net/article/135415.htm)

[IO & NIO、select & poll & epoll](https://note.youdao.com/)

[浅谈计算机中的IO模型](https://www.cnblogs.com/renpingsheng/p/7221116.html)

[Java NIO：浅谈I/O 模型](https://www.cnblogs.com/dolphin0520/p/3916526.html)

