package com.example.javajuc.ConfitionDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition01 {
    public static final ReentrantLock lock = new ReentrantLock();
    public static final Condition condition = lock.newCondition();
    //是否修改过数据 修改过就可以读取了
    public static boolean flag = true;

    public static Integer shareData = 1;

    // 修改数据方法 如果数据没有被读取过则一直await 释放lock
    public static void change() {
        lock.lock();
        try {
            while (!flag) {
                condition.await();
            }
            shareData++;
            flag = false;
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void read() {
        lock.lock();
        try {
            while (flag) {
                condition.await();
            }
            // 修改flag状态之后通知condition
            flag = true;
//            signal 唤醒而不是 notify
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (; ; ) {
                Condition01.change();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("change" + shareData);
            }
        },"product").start();
        new Thread(() -> {
            for (; ; ) {
                Condition01.read();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("read" + shareData);
            }
        },"consumer").start();
    }
}
