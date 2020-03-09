package edu.eci.arep.laboratorio5.server;

import java.net.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class HttpSserver {
	static PrintWriter out;
	
	static BufferedReader in;
	
	static ServerSocket serverSocket = null;
	
	 static Socket clientSocket = null;
	 private static ExecutorService ExecutorService;
	 private static final int hilos = 25;

	
	
  public static void main(String[] args) throws IOException {
	  
	  	
	   serverSocket = null;
	   boolean running =true;
	   try { 
	      serverSocket = new ServerSocket(getPort());
	   } catch (IOException e) {
	      System.err.println("Could not listen on port: 35000.");
	      System.exit(1);
	   }
	   
	   setExecutorService(Executors.newFixedThreadPool(hilos));
	   while(running) {	   
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
		   
		   serverSocket.close();
		   ExecutorService.shutdown();		    
		  		    
	   }
  }
  
  
  static int getPort() {
      if (System.getenv("PORT") != null) {
          return Integer.parseInt(System.getenv("PORT"));
      }        
         
      return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
  }

public static ExecutorService getExecutorService() {
	return ExecutorService;
}

public static void setExecutorService(ExecutorService executorService) {
	ExecutorService = executorService;
}
  
}
