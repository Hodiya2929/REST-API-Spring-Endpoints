package com.allowed.connections.environment.rest.endpoints;

import java.util.List;



public class Connection {

 private List<String> allowedHosts; 
	
	public Connection(List<String> allAlowedIds)
	{
		this.allowedHosts = allAlowedIds;
	}

	public List<String> getAllowedHosts() {
		return allowedHosts;
	}
	
}
