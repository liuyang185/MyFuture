package com.proxy;

/**
 * @Description
 * @Author YCKJ1423
 * @Date 2020/7/24 14:30
 * Version 1.0
 **/
public class BlackPerson implements Person {
    @Override
    public String getName() {
        return Thread.currentThread().getName()+"l am black";
    }

    @Override
    public String compare(Person person) {
        return person.getName();
    }

    @Override
    public String getAge(String s) {
        return null;
    }
}
