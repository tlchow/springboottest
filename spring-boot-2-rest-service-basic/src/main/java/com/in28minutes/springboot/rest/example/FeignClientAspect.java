package com.in28minutes.springboot.rest.example;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeignClientAspect {
	@AfterReturning(pointcut = "execution(* *..*FeignClient.*(..))", returning = "result")
    public void printServiceName(JoinPoint joinPoint, Object result) {
		java.lang.reflect.Type type = joinPoint.getTarget().getClass().getGenericInterfaces()[0];
        Annotation annotation = ((Class)type).getAnnotation(FeignClient.class);
        if (annotation != null && annotation instanceof FeignClient) {
            FeignClient feignClient = (FeignClient) annotation;
            String serviceName = feignClient.name();
            System.out.println("serviceName=" + serviceName);
            System.out.println("feignClient=" + feignClient);
            System.out.println("result=" + result.toString());
        }
    }
}