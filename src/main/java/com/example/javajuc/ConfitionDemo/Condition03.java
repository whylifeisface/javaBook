package com.example.javajuc.ConfitionDemo;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition03 {
    //改进Condition02  signal有可能 当list 超过最大的时候 唤醒的有可能是 add 方法并且什么都不执行就结束
//    我们现在使用两个condition 来解决问题 当队列满了使用FullCondition 来await 当队列

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition FullCondition = lock.newCondition();
    public static Condition condition = lock.newCondition();

    public static Integer data = 1;
    public static LinkedList<Integer> linkedList = new LinkedList<>();

    public static Integer MaxSize = 100;

    public static void add() throws InterruptedException {
        lock.lock();

        while (linkedList.size() >= MaxSize) {
            FullCondition.await();
        }
        linkedList.add(data);
        data++;
        condition.signal();
        lock.unlock();
    }
    public static void remove() {
        lock.lock();
        linkedList.remove();
        FullCondition.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        new Thread( () -> {
            try {
                Condition03.add();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("add" + data);
        },"add").start();
        new Thread( () -> {
            Condition03.remove();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("remove" + data);
        },"remove").start();
    }
}
