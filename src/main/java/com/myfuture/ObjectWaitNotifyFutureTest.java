package com.myfuture;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/1 10:30
 * Version 1.0
 **/
public class ObjectWaitNotifyFutureTest {
    public static void main(String[] args) {
        ObjectWaitNotifyFuture future = new ObjectWaitNotifyFuture(new ApCallable() {
            @Override
            public String call() {
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
