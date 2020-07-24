package com.myfuture;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/6/30 19:57
 * Version 1.0
 **/
public class LockSupportFuture<V> implements ApFuture, Runnable {
    private ApCallable<V> callable;
    private Object outcome;
    private List<Thread> waitList = new ArrayList<>();

    public LockSupportFuture(ApCallable<V> callable) {
        this.callable = callable;
    }

    @Override
    public Object get() {
        if (outcome == null) {
            System.out.println(Thread.currentThread().getName()+"will park");
            waitList.add(Thread.currentThread());
            LockSupport.park(this);
        }
        return outcome;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "step in");
        V v = callable.call();
        outcome = v;
        System.out.println(Thread.currentThread().getName() + "call finish");
        for(Thread thread:waitList){
            LockSupport.unpark(thread);
        }

    }
}
