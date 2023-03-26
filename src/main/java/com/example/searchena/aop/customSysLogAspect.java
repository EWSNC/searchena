package com.example.searchena.aop;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class customSysLogAspect {

    @Pointcut("@annotation(com.example.searchena.annation.SysLog)")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        UUID requestId = UUID.randomUUID(false);

        StopWatch stopWatch = new StopWatch();
        log.info("method request {} start ========== {}#{} ", requestId, className, methodName);

        stopWatch.start();
        Object proceed = point.proceed();
        stopWatch.stop();

        log.info("method request {} end ========== {}#{}  consumer time : {}ms ", requestId, className, methodName, stopWatch.getTotalTimeMillis());

        return proceed;
    }

}
