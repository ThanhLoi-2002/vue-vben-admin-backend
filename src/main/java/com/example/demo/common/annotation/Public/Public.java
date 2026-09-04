package com.example.demo.common.annotation.Public;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // Dùng được trên cả Method hoặc cả Class Controller
@Retention(RetentionPolicy.RUNTIME)
public @interface Public {
}
