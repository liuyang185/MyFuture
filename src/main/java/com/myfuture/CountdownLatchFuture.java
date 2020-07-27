package com.myfuture;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/27 14:42
 * Version 1.0
 **/
public class CountdownLatchFuture {

    private Future<Object> addSearchTask1() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .build();

        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Object> value = new AtomicReference<>();
        final AtomicReference<Throwable> throwable = new AtomicReference<>();

        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                throwable.compareAndSet(null,e);
                latch.countDown();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    value.set(response.body().toString());
                }catch (Exception e){
                    throwable.compareAndSet(null,e);
                }finally {
                    latch.countDown();
                }
            }
        });
        return  new Future<Object>() {
            private volatile boolean cancelled;

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if(latch.getCount()>0){
                    cancelled = true;
                    call.cancel();
                    latch.countDown();
                    return true;
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return this.cancelled;
            }

            @Override
            public boolean isDone() {
                if(latch.getCount()==0){
                    return true;
                }
                return false;
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                latch.wait();
                return getValue();
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                if (latch.await(timeout, unit)) {
                    return value.get();
                } else {
                    throw new TimeoutException("Timed out after " + unit.toMillis(timeout) + "ms waiting for underlying Observable.");
                }
            }
            private Object getValue() throws ExecutionException {
                Throwable e = throwable.get();
                if(e!=null){
                    throw new ExecutionException("Observable onError", e);
                }
                else if (this.cancelled) {
                    throw new CancellationException("Subscription unsubscribed");
                }
                else {
                    return value.get();
                }
            }
        };
    }
}
