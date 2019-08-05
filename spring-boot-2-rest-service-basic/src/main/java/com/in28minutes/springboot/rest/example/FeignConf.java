package com.in28minutes.springboot.rest.example;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.Logger;

@Configuration
public class FeignConf {
	
	public static class InfoLoggerFactory implements FeignLoggerFactory {

	    @Override
	    public Logger create(Class<?> type) {
	        return new InfoLoger(type);
	    }
	}

	public static class InfoLoger extends feign.Logger {

		private org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());

		public InfoLoger(Class<?> clazz) {
			this(LogManager.getLogger(clazz));
		}

		public InfoLoger(String name) {
			this(LogManager.getLogger(name));
		}

		public InfoLoger(org.apache.logging.log4j.Logger logger) {
			this.logger = logger;
		}

		@Override
		protected void logRequest(String configKey, Level logLevel, Request request) {
			if (logger.isInfoEnabled()) {
				super.logRequest(configKey, logLevel, request);
			}
		}

		@Override
		protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
				throws IOException {
			if (logger.isInfoEnabled()) {
				return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
			}
			return response;
		}

		@Override
		protected void log(String configKey, String format, Object... args) {
			if (logger.isInfoEnabled()) {
				logger.info(String.format(methodTag(configKey) + format, args));
			}
		}
	}
	
	public static class CustomRetry extends feign.Retryer.Default implements feign.Retryer {
		private org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());

	    private final int maxAttempts;
	    private final long backoff;
	    int attempt;

	    public CustomRetry() {
	        this(1000, 3);
	    }

	    public CustomRetry(long backoff, int maxAttempts) {
	        this.backoff = backoff;
	        this.maxAttempts = maxAttempts;
	        this.attempt = 1;
	    }

	    public void continueOrPropagate(RetryableException e) {
	    	logger.warn("Error when sending request in feignClient retry, {}/{}, sleep {}, cause {}", attempt, maxAttempts, backoff, e.getCause());
	        if (attempt++ >= maxAttempts) {
	            throw e;
	        }

	        try {
	            Thread.sleep(backoff);
	        } catch (InterruptedException ignored) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    @Override
	    public Retryer clone() {
	        return new CustomRetry(backoff, maxAttempts);
	    }
	}
	
    @Bean
    public Retryer retryer() {
        return new CustomRetry();
    }
	
    @Bean
    FeignLoggerFactory infoFeignLoggerFactory() {
        return new InfoLoggerFactory();
    }

}
