
[TOC]
- [🌈soul](#%F0%9F%8C%88soul)
  - [🗽soul-blog](#%F0%9F%97%BDsoul-blog)
  - [🍩soul-common](#%F0%9F%8D%A9soul-common)
  - [🌎soul-dependencies](#%F0%9F%8C%8Esoul-dependencies)
  - [🍄soul-example](#%F0%9F%8D%84soul-example)
    - [🌏soul-learn](#%F0%9F%8C%8Fsoul-learn)
    - [🌵soul-springboot](#%F0%9F%8C%B5soul-springboot)
    - [🌍soul-test](#%F0%9F%8C%8Dsoul-test)
  - [😇soul-unique-id](#%F0%9F%98%87soul-unique-id)
    - [方案一](#%E6%96%B9%E6%A1%88%E4%B8%80)

# 🌈soul
玩具项目，记录各种学习

## 🗽soul-blog

博客文章

## 🍩soul-common

基本共用的方法集合

## 🌎soul-dependencies

用来自定义模块的管理项目依赖关系

## 🍄soul-example
样例
### 🌏soul-learn

### 🌵soul-springboot

### 🌍soul-test

### soul-xxx(spring-cloud 学习)
Spring Cloud 为开发者提供了在分布式系统
（如配置管理、服务发现、断路器、智能路由、微代理、
控制总线、一次性 Token、全局锁、决策竞选、分布式会话和集群状态）操作的开发工具。使用 Spring Cloud 开发者可以快速实现上述这些模式。

## 😇soul-unique-id

唯一 Id 生成器
原生snowflake 进行整合的

### 方案一
特点：
32位不重复的 id

> 4位appId  17位时间戳  6位机器号  5位数递增

优化 1：
在时间戳的计算上改为 java8 方式，下面这种方式效率比较低
```new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));```

如何缩减长度？

1. appId 可以取模 两位数

2. 年份可以去掉前面两位
