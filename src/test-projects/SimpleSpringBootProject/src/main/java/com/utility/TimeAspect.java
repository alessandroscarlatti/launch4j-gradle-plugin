package com.utility;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {
    @Around("execution(@com.utility.Time * *(..))")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object result = pjp.proceed();
        long end = System.nanoTime();

        long duration = end - start;

        System.out.println("Duration: " + duration + "nano secs.");
        return result;
    }
}