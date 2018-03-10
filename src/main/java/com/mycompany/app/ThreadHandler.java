package com.mycompany.app;
import java.io.*;
import java.net.*;

public class ThreadHandler extends Thread {
	Socket newsocket;
	int n;
	
	ThreadHandler(Socket s, int v){
		newsocket = s;
		n = v;
	}
	
	public void run() {
		try {
			PrintWriter outp = new PrintWriter(newsocket.getOutputStream(), true);
			BufferedReader inp = new BufferedReader(new InputStreamReader(newsocket.getInputStream()));
		
			outp.println("Hello :: enter QUIT to exit \n");
			boolean more_data = true;
			String line;
			while (more_data) {
				line = inp.readLine();
				System.out.println("Message '" + line + "' echoed back to client.");
				if (line == null) {
					System.out.println("line = null");
					more_data = false;
				} else {
					outp.println("From server: " + line + ". \n");
                if (line.trim().equals("QUIT"))
                	more_data = false;
            	}
            }
            newsocket.close();
            System.out.println("Disconnected from client number: " + n);
        } catch (Exception e) {
            System.out.println("IO error " + e);
        }
    }
}


