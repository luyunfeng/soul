
分布式事务   
缓存  
分布式锁  redlock     
JVM  
Spring  
Java 基础 (极客时间)   
基本算法和数据结构  
mysql   
分布式共识算法(注册中心的一些机制)

系列学习
https://mp.weixin.qq.com/mp/homepage?__biz=MzIxMTE0ODU5NQ==&hid=7&sn=0be331e80ba76f3ef22931bed0a14bb8
https://mp.weixin.qq.com/s/bvMtxho3aHp3ksreDNwvSg

# 待学习区域

## 传值和地址

 https://www.cnblogs.com/wchxj/p/8729503.html  
Java中其实还是值传递的，只不过对于对象参数，值的内容是对象的引用。

## Eureka与ZooKeeper 的比较（转）
http://www.cnblogs.com/zgghb/p/6515062.html

## 互联网 Java 工程师进阶知识完全扫盲
https://github.com/doocs/advanced-java

## 时间轮算法
https://blog.csdn.net/wltsysterm/article/category/7459601

## 锁的文章
https://blog.csdn.net/mmoren/article/details/79185862
https://mp.weixin.qq.com/s/_xazrXa8MBYaz2WaX6BNew
https://mp.weixin.qq.com/s/7X0fz0zxsaYF0K3Vtisueg
https://mp.weixin.qq.com/s/rEqPl1fTJJQbqaPEc6yubQ

## 正向代理和反向代理的区别

虽然正向代理服务器和反向代理服务器所处的位置都是客户端和真实服务器之间，所做的事情也都是把客户端的请求转发给服务器，再把服务器的响应转发给客户端，但是二者之间还是有一定的差异的。
1、正向代理其实是客户端的代理，帮助客户端访问其无法访问的服务器资源。反向代理则是服务器的代理，帮助服务器做负载均衡，安全防护等。
2、正向代理一般是客户端架设的，比如在自己的机器上安装一个代理软件。而反向代理一般是服务器架设的，比如在自己的机器集群中部署一个反向代理服务器。
3、正向代理中，服务器不知道真正的客户端到底是谁，以为访问自己的就是真实的客户端。而在反向代理中，客户端不知道真正的服务器是谁，以为自己访问的就是真实的客户端。
4、正向代理和反向代理的作用和目的不同。正向代理主要是用来解决访问限制问题。而反向代理则是提供负载均衡、安全防护等作用。二者均能提高访问速度。
https://www.jianshu.com/p/967603fc1343

## mybatis 面试题 
https://zhuanlan.zhihu.com/p/34469960

## redis 分布式锁
https://blog.csdn.net/qq40988670/article/details/85273966
https://www.jianshu.com/p/f302aa345ca8

## Java基础
https://www.zhihu.com/question/305967405/answer/588023637

## threadLocal
https://mp.weixin.qq.com/s/ZsmZKrY-Z8LrbZ7ap5Kp2A

##  循环依赖
http://cmsblogs.com/?p=2887
三级缓存机制
singthonObjects   实例化好的单例对象
earlySingletonObjects  提前曝光的单例对象（没有完全装配好）
singletonFactories  里面存放的是要被实例化的对象的对象工厂

## IOC 初始化总结
http://cmsblogs.com/?p=2790

Resource 定位和加载
BeanDefinition  装载
标签的解析和校验生成 Document
BeanDefinition 注册

## 注入依赖原理
接口注入和构造器注入

## Bean 的生命周期
http://cmsblogs.com/?p=4034

第一阶段：bean 实例化   doCreateBean() ，策略模式”来决定采用哪种方式来实例化 bean，一般有反射和 CGLIB 动态字节码两种方式

第二阶段：激活 Aware  各种 xxxAware 接口方法的回调
BeanNameAware、BeanClassLoaderAware、BeanFactoryAware、BeanApplicationContextAware 
 
第三阶段：@postConstruct方法

第四阶段：BeanPostProcessor 增强处理

第五阶段：InitializingBean 的 afterPropertiesSet()

第六阶段：init-method
 
第七阶段：@preDestroy
第八节段：执行 beanDestory  destroy

第九节段：执行 destroy-method
