package com.in28minutes.springboot.rest.example;

import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;


@Component
public class CustomContainer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		StuckThreadDetectionValve valve = new StuckThreadDetectionValve();
		valve.setInterruptThreadThreshold(30);
		valve.setThreshold(30);
		factory.addContextValves(valve);
		factory.setBackgroundProcessorDelay(1);
		factory.setPort(8888);
		//factory.addConnectorCustomizers(connector -> connector.addUpgradeProtocol(new Http2Protocol()));
	}

}
