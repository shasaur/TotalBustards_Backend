package com.mycompany.app;

import java.io.*;
import java.net.*;

public class BusServer {
	
	public static void main(String[] args) {
		System.out.println("Starting Server on 8080");
		BusServer testServer = new BusServer();
		testServer.initServer();
	}
	
	
	public void initServer() {
		try {
			ServerSocket socket = new ServerSocket(8080);
			for (;;) {
				Socket newsocket = socket.accept();
				System.out.println("Creating thread... ");
				Thread t = new ThreadHandler(newsocket,1);
				t.start();
			}
		}
		catch (IOException e) {
			System.err.println("Failed to start on port 8080");
		}
	}
}
