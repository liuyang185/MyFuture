package com.myfuture;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/1 9:46
 * Version 1.0
 **/
public class ObjectWaitNotifyFuture implements ApFuture, Runnable {
    private volatile Object outcome;
    private ApCallable callable;

    public ObjectWaitNotifyFuture(ApCallable callable) {
        this.callable = callable;
    }

    @Override
    public  <V>  V get() {
        if(outcome == null){
            synchronized(this){
                try {
                    System.out.println(Thread.currentThread().getName()+"will wait");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        return (V)outcome;
    }

    @Override
    public void run() {
        Object v = callable.call();
        outcome = v;

        synchronized(this){
            System.out.println(Thread.currentThread().getName()+"will notify");
            notifyAll();
            System.out.println(Thread.currentThread().getName()+"notify all");

        }

    }
}
