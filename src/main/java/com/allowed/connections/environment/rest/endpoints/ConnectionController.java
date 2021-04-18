package com.allowed.connections.environment.rest.endpoints;

import com.allowed.connections.environment.network.structure.Environment;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class ConnectionController {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@GetMapping("/api/v1/connections")
	@ResponseBody
	public List<String> getConnectionsRequest(@RequestParam String host_id) {
	
		long requestStart = System.nanoTime();//start record time
		StatisticsInfo.incrementNumOfRequestsByOne();
		
		LOGGER.info( "Server got call number:" + StatisticsInfo.getNumOfRequests() + ". call: " + "/api/v1/connections/" + host_id );

		Environment environment = ConnectionStatisticsApplication.environment;

		boolean resourceExist = environment.checkIfHostExistByHostId(host_id);

		if(!resourceExist) //handling in wrong host_id case - throwing a custom error
		{	
			LOGGER.warning(host_id + " is not exist in the environment - returned " + HttpStatus.NOT_FOUND);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "host id: " + host_id + " is not in the network environment" );
		}


		Map<String, Set<String>> map = 	environment.getAllowedHostsByFwRules();
		List<String> hostLabels = environment.getHostLabesByHostId(host_id);
		Set<String> allAllowedLabels = environment.getAllAlloweddHostsInLabelFormFromFirewallRules(hostLabels, map);
		List<String> allAlowedIds = environment.getAllAllowedHostInIdForm(allAllowedLabels);

		Connection connection = new Connection(allAlowedIds) ;

		long currentReqTime = System.nanoTime() - requestStart;
		//from nanoseconds to milliseconds
		double totalReqTime = ((double)currentReqTime)/1000000;
		StatisticsInfo.addCurrentRequestTimeToTotalTime(totalReqTime);

		return connection.getAllowedHosts();
	}

}
