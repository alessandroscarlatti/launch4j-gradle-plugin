package com.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Component
@Aspect
public class RecordAspect {
    @Before("execution(@com.utility.Record * *(..))")
    public void recordArgs(JoinPoint joinPoint) throws Throwable {
            Arrays.stream(joinPoint.getArgs())
                .forEach(item -> {
                    System.out.println("item: " + item);
                });
    }

    @Around("execution(@com.utility.Record * *(..))")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("args: " + Arrays.asList(pjp.getArgs()));
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("Error recording method invocation...", throwable);
        }
        System.out.println("return value: " + result);
        return result + " (modified!)";
    }
}