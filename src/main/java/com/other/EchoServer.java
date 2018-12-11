package com.other;

import java.io.*;
import java.net.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class EchoServer {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 6666");
            System.exit(-1);
        }

        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Accept failed: 6666");
                System.exit(-1);
            }
            final Socket finalClientSocket = clientSocket;
            threadPoolExecutor.execute(() -> {
                try {
                    PrintWriter out = new PrintWriter(finalClientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(finalClientSocket.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        out.println(inputLine + " " + finalClientSocket.hashCode() + " " + threadPoolExecutor.getActiveCount());
                    }
                    out.close();
                    in.close();
                    finalClientSocket.close();
                    finalClientSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}