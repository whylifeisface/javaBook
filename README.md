run some demo about Java高并发编程详解 深入理解并发核心库
2023 6 7
```agsl
measurement // 实际会记入性能参考的次数
warmup // 预热的次数 用来优化jvm
//都有注解和对应构造Options
```
## BenchmarkMode
AverageTime (调用一次要花费的实际)

Throughput (方法吞吐量 -> 单位时间运行次数)

SampleTime (时间采样)
SingleShotTime (冷测试??)

多Mode

## OutputTimeUnit
统计结果显示单位

## State
1. BenchMark 
2. Thread
3. Group


Thread : 每一个线程会持有一个对象.

BenchMark: 多个线程共享一个对象.

Group:  对数据在多线程下同时被读写的测试

## Param

可以配置 目的是减少重复代码
详情见JMHExample03

## Setup And  TearDown
Setup :
在每一个基准测试方法执行前被调用

TearDown :
在每一个基准测试方法执行后被调用

Level
@Benchmark k  fork s
[measurementIterations(n) + warmupIterations(m)] * @Thread(j)

Setup(Level.Trial):

在每一个基准测试方法的所有批次的前后被执行

= k * s

Setup(Level.Iteration):

在每一个基准测试方法的前后被执行

= k  * s * (N+M)N*J

Setup(Level.Invocation):

好多好多次


## 编写好多代码注意事项
1. 避免DCE (jvm 为我们擦去了一些上下文无关的代码)
2. 