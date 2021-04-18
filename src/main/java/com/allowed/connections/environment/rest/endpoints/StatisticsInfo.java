package com.allowed.connections.environment.rest.endpoints;

import java.util.concurrent.atomic.AtomicLong;

import com.google.common.util.concurrent.AtomicDouble;

public class StatisticsInfo {
	
	private final static long numOfHosts = ConnectionStatisticsApplication.environment.getHosts().size();
	private static AtomicLong numOfRequests = new AtomicLong(0);
	private static AtomicDouble timeToHandleAllRequests = new AtomicDouble(0.0);
	
	
	public static void incrementNumOfRequestsByOne()
	{
		numOfRequests.incrementAndGet();
	}
	
	public static void addCurrentRequestTimeToTotalTime(double value)
	{
		timeToHandleAllRequests.addAndGet(value);
	}
	
	public static long getNumOfRequests()
	{
		return numOfRequests.get();
	}
	
	public static long getNumOfHosts()
	{
		return numOfHosts;
	}
	
	public static double getAverageRequestTime() {
		return timeToHandleAllRequests.get()/getNumOfRequests();
	}
	
	

}
