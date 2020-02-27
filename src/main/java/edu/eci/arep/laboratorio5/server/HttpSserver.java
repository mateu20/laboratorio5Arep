package edu.eci.arep.laboratorio5.server;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import org.apache.commons.io.FilenameUtils;

public class HttpSserver {
	static PrintWriter out;
	
	static BufferedReader in;
	
	static ServerSocket serverSocket = null;
	
	static Socket clientSocket = null;
	

	
	
  public static void main(String[] args) throws IOException {
	  
	  
	   serverSocket = null;
	   try { 
	      serverSocket = new ServerSocket(getPort());
	   } catch (IOException e) {
	      System.err.println("Could not listen on port: 35000.");
	      System.exit(1);
	   }
	   while(true) {	   
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
		   out = new PrintWriter(
		                         clientSocket.getOutputStream(), true);
		   in = new BufferedReader(
		                         new InputStreamReader(clientSocket.getInputStream()));
		   
		   
		   StringBuilder stringBuilder = new StringBuilder();
		   
		   String inputLine, outputLine;
		   
		   Matcher matcher = null;
		   Pattern pattern = Pattern.compile("GET /([^\\s]+)");
	       
		   
		   while ((inputLine = in.readLine()) != null) {
		      System.out.println("RecibÃ­: " + inputLine);
		      stringBuilder.append(inputLine);
		      if (!in.ready()) {
		    	  matcher = pattern.matcher(stringBuilder.toString());
	              if (matcher.find()) {
	                  String req = matcher.group().substring(5);
	                  System.out.println("VALUE: " + req);
	                  returnRequest(req);
	              }
		    	  
		    	  break; }
		   }
		  
		    out.close(); 
		    in.close(); 
		    clientSocket.close(); 
		    
	   }
  }
  
  public static void returnRequest(String req) throws IOException {
	  
	  
	  String path = "src/main/resources/";
      String n = FilenameUtils.getExtension(req);
      if (n.equals("js")) {
    	  path=path+"js/";
    	  
      }else if (n.equals("png") || n.equals("jpg")) {
    	  path=path+"img/";
      }
      
      System.out.println(path+req);
      File file = new File(path+req);
      
      if (file.exists() && !file.isDirectory()) {
	      if (n.equals("png") || n.equals("jpg")) {
	    	  	
	    	  	
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
				DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
				binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
				binaryOut.writeBytes("Content-Type: image/"+n+"\r\n");
				binaryOut.writeBytes("Content-Length: " + data.length);
				binaryOut.writeBytes("\r\n\r\n");
				binaryOut.write(data);
	
				binaryOut.close();
	    	  
	      }
	      else {
				  out.println("HTTP/1.1 200 \r\nContent-Type: text/html\r\n\r\n");
		    	  BufferedReader br = new BufferedReader(new FileReader(file));
	
	              StringBuilder stringBuilder = new StringBuilder();
	              String st;
	              while ((st = br.readLine()) != null) {
	                  stringBuilder.append(st);
	              }
	              out.println(stringBuilder.toString());
	              br.close();
	      }
      }
      else {
    	  out.println("HTTP/1.1 404 \r\n\r\n<html><body><h1>ERROR 404: NOT FOUND</h1></body></html>");
    	  
      }
	  
  }
  
  static int getPort() {
      if (System.getenv("PORT") != null) {
          return Integer.parseInt(System.getenv("PORT"));
      }        
         
      return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
  }
  
}
