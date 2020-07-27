package com.proxy;

import javax.sound.midi.Soundbank;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/24 14:31
 * Version 1.0
 **/
public class PersonInvocationHandler implements InvocationHandler {
    private Object target;

    public PersonInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!method.getName().equals("toString")){
            System.out.println("before:"+method.getName());
        }
        if(method.getName().equals("getName")){
            MethodHandles.Lookup lookup= MethodHandles.publicLookup();
            MethodType methodType = MethodType.methodType(String.class);


            MethodHandle methodHandle = lookup.findVirtual(String.class,"getAge",methodType);
            Object value = (String)methodHandle.invoke(args);
            System.out.println(value);
        }

        Object result = method.invoke(target,args);


        if(!method.getName().equals("toString")){
            System.out.println("after:"+method.getName());
        }
        return result;
    }
}
