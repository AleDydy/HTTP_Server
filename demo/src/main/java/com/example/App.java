package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        try {
            System.out.println("Server in avvio!");
            ServerSocket server = new ServerSocket(8080);
            while (true) {
                Socket s = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream());

                do {
                    String line = in.readLine();
                    System.out.println(line);
                    if (line == null || line.isEmpty())
                        break;
                } while (true);

                sendFile(out);

                out.flush();
                s.close();
            }
            // server.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'instanza del server");
            System.exit(1);
        }
    }


    public static void sendFile(PrintWriter out){

        try{
            File myObject = new File("test.html");
            Scanner reader = new Scanner(myObject);
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Lenght: " + myObject.length());
            out.println("Server: Java HTTP Server from Pingu: 1.0");
            out.println("Date: " + new Date());
            out.println("Content-Type: text/html; charset=utf-8");
            
            out.println();

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                out.println(data);
            }
            reader.close();
        } catch(FileNotFoundException e){
        out.println("HTTP/1.1 404 OK");
        }
    }
}