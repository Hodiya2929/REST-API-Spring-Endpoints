package com.allowed.connections.environment.rest.endpoints;


import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatisticsController {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	@GetMapping("/api/v1/statistics")
	public Statistics getStatisticsRequest() {
	
		Long requestStart = System.nanoTime();
		
		StatisticsInfo.incrementNumOfRequestsByOne();
		
		LOGGER.info( "Server got call number:" + StatisticsInfo.getNumOfRequests()+". call: "  + "/api/v1/statistics");
		
		long hostsNum = StatisticsInfo.getNumOfHosts();
		long requestsNum = StatisticsInfo.getNumOfRequests();
		double averageTime = StatisticsInfo.getAverageRequestTime();
	
		long currentReqTime = System.nanoTime() - requestStart;
		double totalReqTime = ((double)currentReqTime)/1000000; 
		StatisticsInfo.addCurrentRequestTimeToTotalTime(totalReqTime);
	
		Statistics statistics = new Statistics(hostsNum, requestsNum, averageTime);
		return statistics;

	}
}
