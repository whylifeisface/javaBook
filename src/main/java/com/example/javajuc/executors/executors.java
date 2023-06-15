package com.example.javajuc.executors;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class executors {
    public static void testSubmit() throws ExecutionException, InterruptedException {
        ExecutorService executorService =
                Executors.newCachedThreadPool();
        // submit 可以实现 callable 和 runnable 一个有返回值 一个没有
        Future<String> done = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            return "done";
        });
        Future<?> submit = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(submit.get() == null);
        assert submit.get() == null;
        //阻塞获取
        String s = done.get();
        System.out.println(s);
    }

    public static void testExecute() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static class MyThreadFactory implements ThreadFactory {
        private AtomicInteger thread_num_index = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            ThreadGroup group = new ThreadGroup("group1");
            return new Thread(group, r, String.valueOf(thread_num_index.incrementAndGet()));
        }
    }

    public static void createThreadPool() {
        //核心线程数 最少的线程数目
        // 最大线程数
        // 存活时间 时间单位
        // 阻塞队列
        // 线程工厂 一般线程的名字
        // 拒绝策略 丢弃（不接受进入 queue丢掉第一个 ） 异常  阻塞执行

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                Integer.MAX_VALUE, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),
                new MyThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.submit(() -> {
//            Future
            TimeUnit.SECONDS.sleep(1);
            return "";
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        executors.testSubmit();
    }
}
