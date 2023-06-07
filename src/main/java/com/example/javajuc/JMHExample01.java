package com.example.javajuc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 运行模式
@OutputTimeUnit(TimeUnit.MICROSECONDS) // 统计结果显示单位
@State(Scope.Thread) //线程级
@Threads(1) // 给5个线程 结合State 知
public class JMHExample01 {
    private final static String DATA ="DUMMY DATA";
    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Invocation)
    public void setUp(){
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
        System.out.println("11111");
    }
    @Benchmark
    public List<String> arrayListAdd(){
        this.arrayList.add(DATA);
        return arrayList;
    }
    @Benchmark
    public List<String> linkedList(){
        this.linkedList.add(DATA);
        return linkedList;
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts =
                new OptionsBuilder()
                        .include(JMHExample01.class.getSimpleName())
                        .forks(1)
                        .measurementIterations(1) // 实际会记入性能参考的次数
                        .warmupIterations(1) // 预热的次数 用来优化jvm
                        .build();
        new Runner(opts).run();
    }
}
