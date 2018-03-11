package com.mycompany.app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BusServer {
	
	public static void main(String[] args) {
		System.out.println("Starting Server on 8080");
		BusServer testServer = new BusServer();
		testServer.initServer();
	}
	
	
	public void initServer() {
		JsonParser parser = new JsonParser();
		
		HashMap<String, ArrayList<BusStop>> duplicates = new HashMap<String, ArrayList<BusStop>>();
		
		// Collect all duplicates
    	try {
			JsonArray a = (JsonArray) parser.parse(new FileReader("C:/Users/User/Downloads/data.json"));
			for (Object o : a) {
				JsonObject stop = (JsonObject) o;
				String name = stop.get("label").getAsString();
				Double lat = stop.get("lat").getAsDouble();
				Double lon = stop.get("lon").getAsDouble();
				BusStop n = new BusStop(name, new LocXY(lat, lon));
				
				// If no entries for this stop yet, initialise its list
				if (!duplicates.keySet().contains(name)) 
					duplicates.put(name, new ArrayList<BusStop>());
				
				ArrayList<BusStop> busStops = duplicates.get(name);
				busStops.add(n);
				duplicates.put(name, busStops);
	    	}
    	} catch (Exception e) {	
    		e.printStackTrace();
		}
		
    	HashMap<String, BusStop> uniqueBusStops = new HashMap<String, BusStop>();
    	
    	for (ArrayList<BusStop> busStops : duplicates.values()) {
    		double lat = 0;
    		double lon = 0;
    		
    		for (BusStop b : busStops) {
    			lat += b.loc.lat;
    			lon += b.loc.lon;
    		}
    		
    		lat /= (double)busStops.size();
    		lon /= (double)busStops.size();
    		
    		System.out.println(busStops.size());
    		uniqueBusStops.put(busStops.get(0).name, new BusStop(busStops.get(0).name, new LocXY(lat, lon)));
    	}
    	
    	
    	
		try {
			ServerSocket socket = new ServerSocket(8080);
			for (;;) {
				Socket newsocket = socket.accept();
				System.out.println("Creating thread... ");
				Thread t = new ThreadHandler(newsocket,1, uniqueBusStops);
				t.start();
			}
		}
		catch (IOException e) {
			System.err.println("Failed to start on port 8080");
		}
	}
}
