
[TOC]

- [soul](#soul)
  - [soul-common](#soul-common)
  - [soul-dependencies](#soul-dependencies)
  - [soul-example](#soul-example)
    - [soul-learn](#soul-learn)
    - [soul-springboot](#soul-springboot)
    - [soul-test](#soul-test)
  - [soul-unique-id](#soul-unique-id)
    - [方案一](#%E6%96%B9%E6%A1%88%E4%B8%80)
# soul

## soul-common

基本共用的方法集合

## soul-dependencies

用来自定义模块的管理项目依赖关系

## soul-example
存在一些例子和启动类方法
### soul-learn

### soul-springboot

### soul-test

## soul-unique-id

唯一 Id 生成器

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
