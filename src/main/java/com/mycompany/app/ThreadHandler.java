package com.mycompany.app;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ThreadHandler extends Thread {
	Socket newsocket;
	int n;
	
	HashMap<String, BusStop> uniqueBusStops;
	ThreadHandler(Socket s, int v, HashMap<String, BusStop> uniqueBusStops){
		newsocket = s;
		n = v;
		
		this.uniqueBusStops = uniqueBusStops;
	}
	
	public void run() {
		
		try {
			ObjectOutputStream outp = new ObjectOutputStream(newsocket.getOutputStream());
			ObjectInputStream inp = new ObjectInputStream(newsocket.getInputStream());
		
			boolean more_data = true;
			Object request;
			while (more_data) {
				request = inp.readObject();
				
				if (request instanceof PathRequestMsg) {
					PathRequestMsg m = (PathRequestMsg)request;
					
					PathMsg reply = new PathMsg(new ArrayList<BusStop>(uniqueBusStops.values()));
//					for (BusStop b : busStops) {
//			    		if (b.name.equals(m.getStartingPoint())) {
//			    			System.out.println("Lat: " + b.lat + " Lon: " + b.lon);
//			    			lat = b.lat;
//			    			lon = b.lon;
//			    		}
//					}
					
					outp.writeObject(reply);
					
				} else if (request instanceof UpdateUserLocMsg) {
					System.out.println(((UpdateUserLocMsg)request).getLoc().lat + " " + ((UpdateUserLocMsg)request).getLoc().lon);
				}
            }
            newsocket.close();
            System.out.println("Disconnected from client number: " + n);
        } catch (Exception e) {
            System.out.println("IO error " + e);
        }
    }
}


