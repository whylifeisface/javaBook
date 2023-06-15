package com.example.javajuc.juc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHExample04 {


    @State(Scope.Group)
    public static class IntMonitor {
        private int num = 0;

        public void lockInc() {
            ReentrantLock lock = new ReentrantLock();
            try {
                lock.lock();
                num++;
            } finally {
                lock.unlock();
            }
        }

        public void syncInc() {
            synchronized (this) {
                num++;
            }
        }
    }

    @State(Scope.Group)
    public static class AtomicIntegerMonitor {
        private AtomicInteger num = new AtomicInteger(0);

        public void AtomAndInc() {
            num.incrementAndGet();
        }
    }

    @GroupThreads(10)
    @Group("sync")
    @Benchmark
    public void syncInc(IntMonitor monitor) {
        monitor.syncInc();
    }

    @GroupThreads(10)
    @Group("lock")
    @Benchmark
    public void lockInc(IntMonitor monitor) {
        monitor.lockInc();
    }

    @GroupThreads(10)
    @Group("atomic")
    @Benchmark
    public void atomicIntegerInc(AtomicIntegerMonitor monitor) {

        monitor.AtomAndInc();
    }

    public static void main(String[] args) throws RunnerException {
        Options opts =
                new OptionsBuilder()
                        .addProfiler(StackProfiler.class)
                        .include(JMHExample04.class.getSimpleName())
                        .forks(1)
                        .build();
        new Runner(opts).run();

    }
}
