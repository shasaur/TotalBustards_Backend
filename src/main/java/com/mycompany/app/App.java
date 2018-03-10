package com.mycompany.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JsonParser parser = new JsonParser();
    	ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    	try {
			JsonArray a = (JsonArray) parser.parse(new FileReader("C:/Users/User/Downloads/data.json"));
			for (Object o : a) {
				JsonObject stop = (JsonObject) o;
				JsonElement name = stop.get("label");
				//System.out.println(name.getAsString());
				JsonElement lat = stop.get("lat");
				//System.out.println(lat.getAsDouble());
				JsonElement lon = stop.get("lon");
				//System.out.println(lon.getAsDouble());
				BusStop n = new BusStop(name.getAsString(), lat.getAsDouble(), lon.getAsDouble());
				busStops.add(n);
	    	}
    	} catch (Exception e) {	
		}
    	
    	System.out.println("Enter a Stop:");
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine();
    	for (BusStop b : busStops) {
    		if (b.name.equals(input)) {
    			System.out.println("Lat: " + b.lat + " Lon: " + b.lon);
    		}
    	}
    	
    	
        
        
    }
}
