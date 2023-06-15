package com.example.javajuc.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyArrayBlockQueue {
    static ArrayBlockingQueue<AtomicInteger> blockingQueue =
            new ArrayBlockingQueue<>(10);
    static void create() throws InterruptedException {
//        FIFO  有容量
        // add fail to Error

        System.out.println("start");
        Thread product = new Thread(() -> {
            AtomicInteger index = new AtomicInteger(0);
            while (blockingQueue.add(index)) {
                System.out.println("insert" + index);
                index.incrementAndGet();
            }
        });

        // 阻塞等待有内容 才取出
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    AtomicInteger take = blockingQueue.take();
                    System.out.println("take" + take);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //只创建了线程没有run
        product.start();
        consumer.start();
        TimeUnit.SECONDS.sleep(3);
        System.exit(0);
    }

    static void create_thor() throws InterruptedException {
        AtomicInteger index = new AtomicInteger(1);
        new Thread(() -> {
            while (true) {
                try {
                    if (blockingQueue.offer(index,10,TimeUnit.SECONDS)) break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                index.incrementAndGet();
                System.out.println("insert"+index);
            }

        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    AtomicInteger atomicInteger = blockingQueue.poll(10, TimeUnit.SECONDS);
                    System.out.println("poll"+atomicInteger);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
        TimeUnit.SECONDS.sleep(6);
        System.exit(0);
    }
    public static void main(String[] args) throws InterruptedException {
        MyArrayBlockQueue.create_thor();
    }
}
