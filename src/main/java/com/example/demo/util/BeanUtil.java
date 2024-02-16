package com.example.demo.util;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

public class BeanUtil {
    public static void beanCopy(BeanMap beanMap, Object target) {

        Field[] targetDeclaredFields = target.getClass().getDeclaredFields();
        for (Field f : targetDeclaredFields) {
            f.setAccessible(true);
            try {
                Object o = f.get(target);
                String name = f.getName();
                if (ObjectUtils.isEmpty(o)) {
                    f.set(target, beanMap.get(name));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}