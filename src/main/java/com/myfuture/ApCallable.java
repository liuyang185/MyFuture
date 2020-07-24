package com.myfuture;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/6/30 19:58
 * Version 1.0
 **/
@FunctionalInterface
public interface ApCallable<V> {

    V call();
}
