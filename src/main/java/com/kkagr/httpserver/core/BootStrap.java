package com.kkagr.httpserver.core;

import com.kkagr.httpserver.util.Logger;
import com.kkagr.httpserver.util.ServerParser;

import java.net.ServerSocket;
import java.net.Socket;

public class BootStrap {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            Logger.log("httpserver start");
            Long start = System.currentTimeMillis();
            String[] webAppNames = {"oa"};
            WebParser.parser(webAppNames);
            serverSocket = new ServerSocket(ServerParser.getPort());
            Long end = System.currentTimeMillis();
            Logger.log("end-start:"+(end-start));
            while(true){
                clientSocket = serverSocket.accept();
                new Thread(new HandlerRequest(clientSocket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }finally {
            try {

                if(clientSocket != null){
                    clientSocket.close();
                }
                if(serverSocket != null){
                    serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
