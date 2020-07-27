package com.proxy;

import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/24 9:45
 * Version 1.0
 **/
public class ProxyTest {
    public static void main(String[] args) {
//        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Person person = new BlackPerson();
        Person proxy  =(Person) getProxy(person);
        new Thread(()->{
//            System.out.println(proxy.getName());

            proxy.compare(proxy);


        }).start();
    }
    public static Object getProxy(Person person){
       return Proxy.newProxyInstance(person.getClass().getClassLoader()
                ,person.getClass().getInterfaces(),new PersonInvocationHandler(person));
    }
}
