package com.engagetech.expenses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
public class ExpensesApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpensesApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ExpensesApplication.class, args);
		logAccessURLs(applicationContext);
	}

	private static void logAccessURLs(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment env = applicationContext.getEnvironment();
		String contextPath = env.getProperty("server.contextPath", "");
		String port = "8080";
		String hostAddress = "unknown";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// do nothing
		}
		LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}{}\n\t"
						+ "External: \thttp://{}:{}{}\n\t"
						+ "Swagger: \thttp://127.0.0.1:{}{}/swagger-ui.html"
						+ "\n----------------------------------------------------------",
				port, contextPath, hostAddress, port, contextPath, port, contextPath);
	}
}
