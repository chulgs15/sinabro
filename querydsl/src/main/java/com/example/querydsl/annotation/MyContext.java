package com.example.querydsl.annotation;

import java.lang.reflect.Field;

public class MyContext {
    private <T> void invokeAnnotation(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
    }
}
