package edu.eci.arep.laboratorio5.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import static edu.eci.arep.laboratorio5.server.HttpSserver.clientSocket;;


public class Request extends Thread {
	

	 	BufferedReader in;
	 	PrintWriter out;
	    public Request(Socket clientSocket) throws IOException {
	        out = new PrintWriter(
	            clientSocket.getOutputStream(), true);
	        in = new BufferedReader(
	            new InputStreamReader(clientSocket.getInputStream()));
	        iniciar();
	
	
	    }

		private void iniciar() throws IOException {
			// TODO Auto-generated method stub
			 StringBuilder stringBuilder = new StringBuilder();
			   
			   String inputLine;
			   
			   Matcher matcher = null;
			   Pattern pattern = Pattern.compile("GET /([^\\s]+)");
		       
			   
			   while ((inputLine = in.readLine()) != null) {
			      System.out.println("Recibi­: " + inputLine);
			      stringBuilder.append(inputLine);
			      if (!in.ready()) {
			    	  matcher = pattern.matcher(stringBuilder.toString());
		              if (matcher.find()) {
		                  String req = matcher.group().substring(5);
		                  System.out.println("VALUE: " + req);
		                  returnReq(req);
		              }
			    	  
			    	  break; 
			    	  }
			   }
			   out.close(); 
			    in.close(); 
			    clientSocket.close(); 

		}
		

		private void returnReq(String req) throws IOException {
			// TODO Auto-generated method stub
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
		
		
		
		
}