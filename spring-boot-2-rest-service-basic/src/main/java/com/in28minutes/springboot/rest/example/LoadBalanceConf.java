package com.in28minutes.springboot.rest.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicy;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryPolicy;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.stats.StatisticsListener;

@Configuration
public class LoadBalanceConf {
	@Bean
	public LoadBalancedRetryFactory loadBalancedRetryPolicyFactory(
			final SpringClientFactory clientFactory) {
		return new RibbonLoadBalancedRetryFactory(clientFactory) {
            @Override
            public BackOffPolicy createBackOffPolicy(String service) {
                return new NoBackOffPolicy();
            }

            @Override
        	public RetryListener[] createRetryListeners(String service) {
        		RetryListener[] retryListener = new RetryListener[1];
        		retryListener[0] = new RetryListener() {

        			@Override
        			public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        				System.out.println("RetryListener Open");
        				return true;
        			}

        			@Override
        			public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
        					Throwable throwable) {
        				System.out.println("RetryListener Close");
        			}

        			@Override
        			public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
        					Throwable throwable) {
        				System.out.println("RetryListener onError " + throwable.getMessage());
        				//throwable.printStackTrace();
        				
        			}
        			
        		};
        		return retryListener;
        	}
			
		};
	}
	
	/*
	@Bean
    LoadBalancedRetryFactory retryFactory() {
        return new LoadBalancedRetryFactory() {
        	
        	
            @Override
            public BackOffPolicy createBackOffPolicy(String service) {
                return new NoBackOffPolicy();
            }
        	
            @Override
        	public LoadBalancedRetryPolicy createRetryPolicy(String service,
        			ServiceInstanceChooser serviceInstanceChooser) {
        		RibbonLoadBalancerContext lbContext = this.clientFactory
        				.getLoadBalancerContext(service);
        		return new RibbonLoadBalancedRetryPolicy(service, lbContext,
        				serviceInstanceChooser, clientFactory.getClientConfig(service));
        	}

            @Override
        	public RetryListener[] createRetryListeners(String service) {
        		RetryListener[] retryListener = new RetryListener[1];
        		retryListener[0] = new RetryListener() {

        			@Override
        			public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        				System.out.println("RetryListener Open");
        				return true;
        			}

        			@Override
        			public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
        					Throwable throwable) {
        				System.out.println("RetryListener Close");
        			}

        			@Override
        			public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
        					Throwable throwable) {
        				System.out.println("RetryListener onError " + throwable.getMessage());
        				throwable.printStackTrace();
        				
        			}
        			
        		};
        		return retryListener;
        	}

        };
    }
    */
}
