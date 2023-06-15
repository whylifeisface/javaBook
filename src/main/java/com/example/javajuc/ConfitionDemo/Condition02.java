package com.example.javajuc.ConfitionDemo;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition02 {
    // 设计一个程序 实现当长度为100的链表没有满的时候插入数据  队列满地时候阻塞等待

    //链表
    public static LinkedList<Integer> list = new LinkedList<>();
    //链表最大长度
    public static Integer MaxSize = 100;

    //锁
    public static ReentrantLock lock = new ReentrantLock();

    public static Condition condition = lock.newCondition();

    public static Integer data = 0;

    //添加方法
    public static void add() {
        lock.lock();
        try {
            while (list.size() >= MaxSize)
                condition.await();
            list.add(data);
            data++;
            //通知取数据
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void remove() {
        lock.lock();
        try {
            while (list.size() == 0)
                condition.await();
            list.remove();
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (;;) {
                Condition02.add();
                System.out.println("add" + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "product").start();
        TimeUnit.SECONDS.sleep(5);
        new Thread(() -> {
            for (;;){
                Condition02.remove();

                System.out.println("remove" + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        },"consumer").start();
    }
}
