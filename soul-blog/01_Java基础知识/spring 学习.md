[toc]
- [Spring支持的几种bean的作用域](#spring%E6%94%AF%E6%8C%81%E7%9A%84%E5%87%A0%E7%A7%8Dbean%E7%9A%84%E4%BD%9C%E7%94%A8%E5%9F%9F)
- [Spring Bean 加载顺序](#spring-bean-%E5%8A%A0%E8%BD%BD%E9%A1%BA%E5%BA%8F)
- [Spring框架中bean的生命周期](#spring%E6%A1%86%E6%9E%B6%E4%B8%ADbean%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F)
  - [生命周期相关的方法](#%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F%E7%9B%B8%E5%85%B3%E7%9A%84%E6%96%B9%E6%B3%95)
  - [Bean在创建的过程顺序](#bean%E5%9C%A8%E5%88%9B%E5%BB%BA%E7%9A%84%E8%BF%87%E7%A8%8B%E9%A1%BA%E5%BA%8F)
  - [Bean在销毁的过程顺序](#bean%E5%9C%A8%E9%94%80%E6%AF%81%E7%9A%84%E8%BF%87%E7%A8%8B%E9%A1%BA%E5%BA%8F)
  - [整体顺序代码示例](#%E6%95%B4%E4%BD%93%E9%A1%BA%E5%BA%8F%E4%BB%A3%E7%A0%81%E7%A4%BA%E4%BE%8B)
- [自动装配](#%E8%87%AA%E5%8A%A8%E8%A3%85%E9%85%8D)
  - [@Bean](#bean)
  - [xml配置文件中使用autowire](#xml%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E4%B8%AD%E4%BD%BF%E7%94%A8autowire)
  - [@Autowire](#autowire)
  - [@Qualifier](#qualifier)
  - [@Resource](#resource)
- [参考资料](#%E5%8F%82%E8%80%83%E8%B5%84%E6%96%99)
# Spring支持的几种bean的作用域

缺省的Spring bean 的作用域是Singleton. 

* **singleton** : bean在每个Spring ioc 容器中只有一个实例。
 
* **prototype**：一个bean的定义可以有多个实例。
 
* **request**：每次http请求都会创建一个bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。

* **session**：在一个HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。

* **global-session**：在一个全局的HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。

# Spring Bean 加载顺序

# Spring框架中bean的生命周期
## 生命周期相关的方法

关于在spring 容器初始化 bean 和销毁前所做的操作定义方式有三种：

* 第一种：通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作

> PostConstruct 在构造 方法之后执行，PreDestroy在执行销毁之前执行

* 第二种：通过 在xml中定义init-method 和 destory-method方法

* 第三种：通过bean实现InitializingBean和 DisposableBean接口

> 使用InitializingBean接口实现方法afterPropertiesSet()在属性赋值之后执行
> DisposableBean接口实现的方法destroy()在

## Bean在创建的过程顺序
> Constructor > @PostConstruct >InitializingBean > init-method

## Bean在销毁的过程顺序
> @PreDestroy > DisposableBean > destroy-method

## 整体顺序代码示例
```
<bean id="initAndDestroySeqBean" class="tech.soulcoder.springtest.InitAndDestroySeqBean"
          init-method="initMethod" destroy-method="destroyMethod"/>
```
```
public class InitAndDestroySeqBean implements InitializingBean,
    DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    public InitAndDestroySeqBean() {
        System.out.println("执行InitAndDestroySeqBean: 构造方法");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("执行InitAndDestroySeqBean: postConstruct");
    }

    @Override
    public void afterPropertiesSet(){
        System.out.println("执行InitAndDestroySeqBean: afterPropertiesSet");
    }

    public void initMethod() {
        System.out.println("执行InitAndDestroySeqBean: init-method");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("执行InitAndDestroySeqBean: preDestroy");
    }

    @Override
    public void destroy() {
        System.out.println("执行InitAndDestroySeqBean: destroy");
    }

    public void destroyMethod() {
        System.out.println("执行InitAndDestroySeqBean: destroy-method");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("执行setBeanName方法: " + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("执行setBeanFactory方法");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("执行setApplicationContext方法");
    }
}
```
测试运行
```
@Test
    public void spring加载顺序() {
        ApplicationContext context =
            new FileSystemXmlApplicationContext("/src/test/resourecs/springtest/spring.xml");
        context.getBean("initAndDestroySeqBean");
        ((FileSystemXmlApplicationContext)context).close();
    }
```

1. 执行InitAndDestroySeqBean: 构造方法  
2. 执行setBeanName方法: initAndDestroySeqBean  
3. 执行setBeanFactory方法   
4. 执行setApplicationContext方法    
5. 执行InitAndDestroySeqBean: @postConstruct  
6. 执行InitAndDestroySeqBean: afterPropertiesSet    
7. 执行InitAndDestroySeqBean: init-method    
8. 执行InitAndDestroySeqBean: @preDestroy  
9. 执行InitAndDestroySeqBean: destroy 
10. 执行InitAndDestroySeqBean: destroy-method  

# 自动装配

## @Bean 
这个不是自动装配但是是产生一个 Bean 快捷的方式

Spring的@Bean注解用于告诉方法，产生一个Bean对象，然后这个Bean对象交给Spring管理。产生这个Bean对象的方法Spring只会调用一次，随后这个Spring将会将这个Bean对象放在自己的IOC容器中。

```
@Service
public class BeanTest {
    /*
       默认在不指定的时候这个bean的名字就是 getBean
       如果需要指定一下名字就可以
       @Bean("你指定的名字")
    */
    @Bean
    public BeanTest getBean(){
        BeanTest bean = new BeanTest();
        System.out.println("调用方法："+bean);
        return bean;
    }
}
public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Object bean1 = context.getBean("getBean");
        System.out.println(bean1);
        Object bean2 = context.getBean("getBean");
        System.out.println(bean2);
    }
}
```
## xml配置文件中使用autowire
这个比较老，下载一般用注解的方式注入

在 ```User``` Bean 中配置 ```autowire="byName"```
```
<bean id="cat" class="com.xxx.Cat"/>
<bean id="dog" class="com.xxx.Dog"/>
    
<bean id="user" class="com.xxx.User" autowire="byName"> 
    <property name="cat" ref="cat"></property>
    <property name="dog" ref="dog"></property>
    <property name="str" value="haha"></property>
</bean>
```
这个时候 在注入 cat 和 dog 采用的是 byName 的方式，如果把 Bean 中的```id = "cat"``` 改掉其他名字，就无法完成注入。

```
<bean id="user" class="com.xxx.User" autowire="byType"> 
    <property name="cat" ref="cat"></property>
    <property name="dog" ref="dog"></property>
    <property name="str" value="haha"></property>
</bean>
```
如果改成 ```autowire="byType"``` 就算没有 id 这个属性也能注入进去

## @Autowire
按类型自动转配的
@Autowired(required=false)  
false，对象可以为null；true，对象必须存对象，不能为null。

## @Qualifier
@Autowired是根据类型自动装配的，加上@Qualifier则可以根据byName的方式自动装配，其中@Qualifier不能单独使用。
```
 @Autowired
 @Qualifier(value="carXXX")
 private Cat cat;
```

## @Resource
@Resource默认按 byName 自动注入,是J2EE提供的

@Resource有两个中重要的属性：name和type 

Spring将@Resource注解的name属性解析为bean的名字(type属性则解析为bean的类型)

如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用 byType自动注入策略。

如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。

@Resource装配顺序
> (1). 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常;

> (2). 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常;

> (3). 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常;

> (4). 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；



# 参考资料

[69道Spring面试题和答案](http://www.importnew.com/19538.html)

[spring加载bean实例化顺序
](https://www.cnblogs.com/fanguangdexiaoyuer/p/5886050.html)

[Spring常见面试题总结（超详细回答）](https://blog.csdn.net/a745233700/article/details/80959716)

[死坑](http://cmsblogs.com/)

[知乎](https://zhuanlan.zhihu.com/p/41961670)

[事务](https://www.jianshu.com/p/9da345f7e542)