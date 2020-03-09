package edu.eci.arep.laboratorio5.client;
import java.net.*; 
import java.io.*; 

public class clienteConcurrente extends Thread{ 
	String page;
	private static int hilos = 25;
	private static URL url;
  
  
  
  public clienteConcurrente(){} 
  
  public static void main(String[] args) throws MalformedURLException, InterruptedException { 
      url = new URL(args[0]); 
      hilos = Integer.parseInt(args[1]); 
      clienteConcurrente[] cl= new clienteConcurrente[hilos];      
      
        for (int i = 0; i < hilos; i++) {
            cl[i] = new clienteConcurrente();
        }
        long startTime = System.nanoTime();
       for (int i = 0; i < hilos; i++) {
            cl[i].start();
        }      
       
        for (int i = 0; i < hilos; i++) {
            cl[i].join();
            System.out.println("Contenido cliente #" + (i+1) + "\n" + cl[i].page);
            
        }
       long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Tiempo total: "+ (double) elapsedTime/1000000000);

    }
  
  @Override
  public void run(){
       try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(url.openStream()))) { 
            page = "";
            String inputLine = null; 
            while ((inputLine = reader.readLine()) != null) { 
                  page=page+inputLine;
             } 
       } catch (IOException x) { 
               System.err.println(x); 
       }
  }
}
