[toc]

# 配置几个关键点

1. 配置 dataSource
```
<bean id="dataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
  <property name="poolProperties">
     <bean class="org.apache.tomcat.jdbc.pool.PoolProperties">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="${datasource.url}"/>
        <property name="username" value="${datasource.username}"/>
        <property name="password" value="${datasource.password}"/>
     </bean>
  </property>
</bean>
```
2. 配置 mybatis-config.xml
```
<configuration>
    <settings>
        <!-- 全局映射器启用缓存 -->
        <setting name="cacheEnabled" value="false"/>
        <!-- 查询时，关闭关联对象即时加载以提高性能 -->
        <setting name="lazyLoadingEnabled" value="false"/>
        <!-- 设置关联对象加载的形态，此处为按需加载字段(加载字段由SQL指 定)，不会加载关联表的所有字段，以提高性能 -->
        <setting name="aggressiveLazyLoading" value="false"/>
        <!-- 对于未知的SQL查询，允许返回不同的结果集以达到通用的效果 -->
        <setting name="multipleResultSetsEnabled" value="true"/>
        <!-- 允许使用列标签代替列名 -->
        <setting name="useColumnLabel" value="true"/>
        <!-- 允许使用自定义的主键值(比如由程序生成的UUID 32位编码作为键值)，数据表的PK生成策略将被覆盖 -->
        <setting name="useGeneratedKeys" value="true"/>
        <!-- 给予被嵌套的resultMap以字段-属性的映射支持 -->
        <setting name="autoMappingBehavior" value="FULL"/>
        <!-- 对于批量更新操作缓存SQL以提高性能 -->
        <setting name="defaultExecutorType" value="SIMPLE"/>
        <!-- 数据库超过25000秒仍未响应则超时 -->
        <setting name="defaultStatementTimeout" value="25000"/>
    </settings>
</configuration>
```
3. 配置 sqlSessionFactory   

```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
   <property name="dataSource" ref="dataSource"/>
    <!--配置-->
   <property name="configLocation" value="classpath:mybatis-config.xml"/>
   <property name="plugins">
     ..
   </property>
</bean>
```
4. 配置 transactionManager

编程式事务主要注入这个Bean 就能开始事务的手动操作
声明式事务也是注入这个Bean
```
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>

```
5. 配置 transactionTemplate
```
<bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
  <property name="transactionManager" ref="transactionManager"/>
</bean>
```
6. 配置扫描目标
```
<!-- dao层Mapper配置-->
<bean id="mapperScannerConfig" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.xxx.dao"/>
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
</bean>
```
# SpringBoot 多数据源配置

https://www.jianshu.com/p/efaf750ac3bb

# 资料
https://www.zhihu.com/question/270387939/answer/360487647