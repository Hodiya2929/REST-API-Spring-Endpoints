package com.allowed.connections.environment.network.structure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Environment {

	private static Environment environment = null;

	private ArrayList<Host> hosts;
	private ArrayList<Fw_Rule> fw_rules;


	public static Environment getEnvironment(String fileName)
	{
		if(environment == null)
		{
			environment = new Environment(fileName);
		}

		return environment;
	}


	/**
	 * This class describes a network. Each network is consist from hosts and
	 * from firewall rules.
	 * A network is constructed from the input files - so the constructor take a file name 
	 * and build an environment from the json file. 
	 * @param jsonFileName - the input file that describe the network structure
	 */
	private Environment(String jsonFileName)
	{
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(jsonFileName);
		Reader reader = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(reader);

		GsonBuilder builder = new GsonBuilder(); 
		Gson gson = builder.create(); 

		Environment environment = gson.fromJson(br, Environment.class); 

		this.fw_rules = environment.fw_rules;
		this.hosts = environment.hosts;
	}


	public ArrayList<Host> getHosts() {
		return hosts;
	}
	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}
	public ArrayList<Fw_Rule> getFw_rules() {
		return fw_rules;
	}
	public void setFw_rules(ArrayList<Fw_Rule> fw_rules) {
		this.fw_rules = fw_rules;
	}

	/**
	 * 
	 * @param host_id - a host_id string to check if exist in the network
	 * @return whether the host is in the network or not
	 */
	public boolean checkIfHostExistByHostId(String host_id)
	{
		boolean hostExist = false;

		hostExist = this.hosts.stream().anyMatch(host->

		(host.getHost_id().equals(host_id))
				);

		return hostExist;
	}

	/**
	 *This method iterate over the firewall rules:
	 *for each rule - add the source host as the key and add the associated destination host - to a set as the key value. 
	 * @return a map - key is the host id and the value is a set of all hosts that are allowed to access this host
	 */
	public Map<String ,Set<String>> getAllowedHostsByFwRules()
	{
		Map<String, Set<String>> allowedHosts = new HashMap<>();

		for (Fw_Rule fw_rule : this.getFw_rules()) {

			String srcHost = fw_rule.getSrc_label();
			Set<String> destHost = null;

			if(allowedHosts.containsKey(srcHost)) {

				destHost = allowedHosts.get(srcHost);
				destHost.add(fw_rule.getDst_label());
			}	
			else {
				destHost = new HashSet<>();
				destHost.add(fw_rule.getDst_label());
				allowedHosts.put(srcHost, destHost);
			}

		}
		return allowedHosts;
	}

	/**
	 * 
	 * @param host_id - host to search in the network, if not exist in the network - throw NoSuchElementException
	 * @return - the labels array 0f the specified host, 
	 * 
	 */
	public List<String> getHostLabesByHostId (String host_id)
	{

		//this.hosts.stream().filter(host->(host.getHost_id().equals(host_id))).collect(Collectors.toList()).get(0).getLabels();

		for (Host currentHost : this.hosts) {

			if(currentHost.getHost_id().equals(host_id))
			{
				return currentHost.getLabels();
			}
		}

		throw new NoSuchElementException("The host id: " + host_id + " is not in the network environment");
	}

	/**
	 * for each host in the network there is a labels list associated.
	 * This method iterates overs all of the labels and for each labels it
	 * add to a set - all of the hosts that can access the host. 
	 * why set? - there are duplicated values and we don't need duplicated items. 
	 * @param labels - the host labels - (list)
	 * @param accessMap - key is the host label and the value are all the hosts that can access it
	 * @return a set of all of the hosts that can access 
	 */
	public Set<String> getAllAlloweddHostsInLabelFormFromFirewallRules(List<String> labels,Map<String, Set<String>> accessMap)
	{

		Set<String> allAllowedHostInLabelForm = new HashSet<>();

		for (String currentHostLabel : labels) {

			if(accessMap.containsKey(currentHostLabel))
			{
				allAllowedHostInLabelForm.addAll(accessMap.get(currentHostLabel));
			}
		}

		return allAllowedHostInLabelForm;
	}

	/**
	 * This method output the REST API endpoint - connections - list of all hosts id 
	 * that allowed to access the input host. 
	 * For all the hosts in the network: get the labels list and search the label in 
	 * the set of all allowed labels - if exist - add this host id.
	 * @param allAllowedLabels - set all labels which can access the input host
	 * @return - list of all of the hosts id that can access the input host
	 */
	public List<String> getAllAllowedHostInIdForm(Set<String> allAllowedLabels){

		ArrayList<String> allowedHostsByIdList = new ArrayList<>();

		for (Host currentHost : this.hosts) {

			ArrayList<String> currentHostLabels = currentHost.getLabels();

			for (String currentLabel : currentHostLabels) {

				if(allAllowedLabels.contains(currentLabel))
				{
					allowedHostsByIdList.add(currentHost.getHost_id());
					break; //because we already added host_id no need to search other labels
				}
			}
		}

		return allowedHostsByIdList;
	}

}
