package com.allowed.connections.environment.rest.endpoints;


import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.allowed.connections.environment.configuration.MyLogger;
import com.allowed.connections.environment.network.structure.Environment;

@SpringBootApplication
public class ConnectionStatisticsApplication {

	public static Environment environment;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	public static void main(String[] args) throws InterruptedException {

		initializeLogger();
		initializeNetworkSingletonFromSource(args[0]);

		SpringApplication.run(ConnectionStatisticsApplication.class, args);

		LOGGER.info("REST API started on localhost port 8080");
	}
	
	private static void initializeNetworkSingletonFromSource(String fileName)
	{
		LOGGER.info("Environment was established from file " + fileName);
		ConnectionStatisticsApplication.environment = Environment.getEnvironment(fileName);
	}

	private static void initializeLogger()
	{
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			//throw new RuntimeException("Problems with creating the log files");
		}
	}

}
