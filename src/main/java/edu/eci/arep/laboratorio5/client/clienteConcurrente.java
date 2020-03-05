package edu.eci.arep.laboratorio5.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class clienteConcurrente extends Thread {
    private int code;
    private String res;
    public static int hilos = 15;
    private URL url;
    public clienteConcurrente(URL url) {
        this.url = url;
        code= 0;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection yc = (HttpURLConnection) url.openConnection();
            code = yc.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            res= sb.toString();
            in.close();
            yc.disconnect();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public int getResponseCode() {
        return code;
    }


    public String getResponse(){
        return res;
    } 

    public static void main(String[] args) throws Exception {
        URL url = new URL(args[0]);
        hilos = Integer.parseInt(args[1]);
        clienteConcurrente[] concurrentClients = new clienteConcurrente[hilos];
        for (int i = 0; i < hilos; i++) {
            concurrentClients[i] = new clienteConcurrente(url);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i <hilos; i++) {
            concurrentClients[i].start();
        }
        int success = 0;
        int fails = 0;
        for (int i = 0; i <hilos; i++) {
            concurrentClients[i].join();
            System.out.println("Response for client " + i + " -> " + concurrentClients[i].getResponse());
            if (concurrentClients[i].getResponseCode() >= 200) {
                success++;
            } else {
                fails++;
            }
        }
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Completado: %d\nFails: %d\nExecuting %d requests in %f seconds\n", success, fails, hilos, (double) elapsedTime/1000000000);

    }
}
