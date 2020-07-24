package com.myfuture;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/6/30 20:15
 * Version 1.0
 **/
public class LockSupportFutureTest {
    public static void main(String[] args) {

        LockSupportFuture future = new LockSupportFuture(new ApCallable() {
            @Override
            public Object call() {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok";
            }
        });
        Thread th1 = new Thread(future);
        th1.setName("future thread-0 ");
        th1.start();

        Thread th2 = new Thread(()->{
            System.out.println(Thread.currentThread()+":result back:"+future.get().toString());

        });
        th2.start();

        System.out.println(Thread.currentThread()+":result back:"+future.get().toString());


    }
}
