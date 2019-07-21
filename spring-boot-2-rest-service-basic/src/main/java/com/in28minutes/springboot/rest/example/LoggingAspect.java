package com.in28minutes.springboot.rest.example;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.in28minutes.springboot.rest.example.student.IKeyRequest;

/**
 * Aspect for logging execution.
 * 
 */
@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LogManager.getLogger(this.getClass());
	

//	/**
//	 * Run before the method execution.
//	 * @param joinPoint
//	 */
//	@Before("execution(* com.in28minutes.springboot.rest.example.student.StudentResource.createStudent(..))")
//	public void logBefore(JoinPoint joinPoint) {
//		log.debug("logBefore running .....");
//		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//		
//	}
//
//	/**
//	 * Run after the method returned a result.
//	 * @param joinPoint
//	 */
//	@After("execution(* com.in28minutes.springboot.rest.example.student.StudentResource.createStudent(..))")
//	public void logAfter(JoinPoint joinPoint) {
//		log.debug("logAfter running .....");
//		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//		for(Object arg: joinPoint.getArgs()) {
//			if(arg instanceof IKeyRequest) {
//				System.out.println("IKeyReust.logKey=" + ((IKeyRequest) arg).logKey());
//			}
//		}
//	}
//
//	/**
//	 * Run after the method returned a result, intercept the returned result as well.
//	 * @param joinPoint
//	 * @param result
//	 */
//	@AfterReturning(pointcut = "execution(* com.in28minutes.springboot.rest.example.StudentResource.createStudent(..))", returning = "result")
//	public void logAfterReturning(JoinPoint joinPoint, Object result) {
//		log.debug("logAfterReturning running .....");
//		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//
//	}
//
	/**
	 * Run around the method execution.
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.in28minutes.springboot.rest.example.student.StudentResource.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (log.isDebugEnabled()) {
				log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}
		} finally {
			long timeTaken = System.currentTimeMillis() - startTime;
			String key = "";
			for(Object arg: joinPoint.getArgs()) {
			if(arg instanceof IKeyRequest) {
				key =((IKeyRequest) arg).logKey();
			}
			log.info("Finish Controller |timeTaken |{} |key |{} |name |{} ", timeTaken, key, joinPoint.getSignature().getName() );
		}
			
			
		}

	}
//
//	/**
//	 * Advice that logs methods throwing exceptions.
//	 *
//	 * @param joinPoint join point for advice
//	 * @param e         exception
//	 */
//
//	@AfterThrowing(pointcut = "execution(* com.in28minutes.springboot.rest.example.student.StudentResource.createStudent(..))", throwing = "error")
//	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
//		log.debug("logAfterThrowing running .....");
//		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
//				joinPoint.getSignature().getName(), error.getCause() != null ? error.getCause() : "NULL");
//	}
	
}