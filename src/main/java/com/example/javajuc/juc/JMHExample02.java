package com.example.javajuc.juc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
@Threads(5)
public class JMHExample02 {
    private Map<Long, Long> concurrentMap;
    private Map<Long, Long> synchroizeMap;

    @Setup
    public void setUp() {
        concurrentMap = new ConcurrentHashMap<>();
        synchroizeMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Benchmark
    public void testConcurrentMap() {
        this.concurrentMap.put(System.nanoTime(),
                System.nanoTime());
    }
    @Benchmark
    public void testSynchronizedMap(){
        this.synchroizeMap.put(System.nanoTime(),
                System.nanoTime());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JMHExample02.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
