package com.example.javajuc.juc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(1)
@Threads(5)
@Measurement(iterations = 10)
@Warmup(iterations = 10)

@OutputTimeUnit(TimeUnit.MILLISECONDS)

public class JMHExample03 {
    //配置 通过switch
    @Param({"1", "2", "3", "4"})
    private int type;
    private Map<Long, Long> map;

    @Setup()
    public void setUp() {
        switch (this.type) {
            case 1: {
                this.map = new ConcurrentHashMap<>();
                break;
            }
            case 2: {
                this.map = Collections
                        .synchronizedMap(new HashMap<>());
                break;
            }
            case 3: {
                this.map = new Hashtable<>();
                break;
            }
            case 4: {
                this.map = new ConcurrentSkipListMap<>();
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal map type.");
            }
        }
    }
    @Benchmark
    public void test(){
        this.map.put(System.nanoTime(),System.nanoTime());
    }
    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample03.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
