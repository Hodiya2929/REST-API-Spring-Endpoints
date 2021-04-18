package com.allowed.connections.environment.rest.endpoints;

public class Statistics {
	
	private final long host_count;
	private final long request_count;
	private final double average_request_time;
	
	
	public Statistics(long host_count, long request_count, double average_request_time) {
		this.host_count = host_count;
		this.request_count = request_count;
		this.average_request_time = average_request_time;
	}


	public long getHost_count() {
		return host_count;
	}


	public long getRequest_count() {
		return request_count;
	}


	public double getAverage_request_time() {
		return average_request_time;
	}

}
