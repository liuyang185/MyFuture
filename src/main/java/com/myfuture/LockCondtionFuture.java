package com.myfuture;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/1 9:46
 * Version 1.0
 **/
public class LockCondtionFuture implements ApFuture, Runnable {
    private volatile Object outcome;
    private ApCallable callable;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public LockCondtionFuture(ApCallable callable) {
        this.callable = callable;
    }

    @Override
    public <V> V get() {
        if (outcome == null) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "will wait");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }

        return (V) outcome;
    }

    @Override
    public void run() {
        Object v = callable.call();
        outcome = v;

        lock.lock();
        System.out.println(Thread.currentThread().getName() + "will notify");
        condition.signalAll();
        System.out.println(Thread.currentThread().getName() + "notify all");
        lock.unlock();

    }
}
