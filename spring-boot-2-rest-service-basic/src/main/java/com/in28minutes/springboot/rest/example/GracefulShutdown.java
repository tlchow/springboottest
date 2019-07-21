package com.in28minutes.springboot.rest.example;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class GracefulShutdown implements ApplicationListener<ContextClosedEvent>, TomcatConnectorCustomizer {

    private static final Logger log = LoggerFactory.getLogger(GracefulShutdown.class);

    private volatile Connector connector;

    @Value("${app.shutdownTimeout}")
    int shutdownTimeout;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.connector.pause();

        Executor executor = this.connector.getProtocolHandler().getExecutor();

        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();

            try {
                if (threadPoolExecutor.awaitTermination(shutdownTimeout, TimeUnit.SECONDS)) {
                    log.warn("Tomcat thread pool did not shutdown gracefully within " + shutdownTimeout
                            + " seconds. Proceeding with forceful shutdown.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }
}