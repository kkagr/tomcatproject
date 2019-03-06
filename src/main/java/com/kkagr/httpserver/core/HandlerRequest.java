package com.kkagr.httpserver.core;

import com.kkagr.httpserver.server.LoginServlet;
import com.kkagr.httpserver.util.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HandlerRequest implements Runnable{
    public Socket clientScoket;
    public PrintWriter out;
    public HandlerRequest(Socket clientScoket){
        this.clientScoket= clientScoket;
    }
    public void run(){
        BufferedReader br = null;
        Logger.log("httpserver thread:"+Thread.currentThread().getName());
        try {

            br = new  BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
            out = new PrintWriter(clientScoket.getOutputStream());
            String requestLine = br.readLine();
            String requestURL = requestLine.split(" ")[1];
            if(requestURL.endsWith(".html") || requestURL.endsWith(".htm")){
                responseStaticPage(requestURL,out);
            }else{
                String servletPath = requestURL;
                if(servletPath.contains("?")){
                    servletPath =servletPath.split("[?]")[0];
                }
                String webAppName = servletPath.split("[/]")[1];
                Map<String,String> servletMap = WebParser.servletMaps.get(webAppName);
                String urlPattern = servletPath.substring(1+webAppName.length());
                String servletClass = servletMap.get(urlPattern);
                if(servletClass != null){
                    ResponseObject responseObj = new ResponseObject();
                    responseObj.setWriter(out);
                    RequestObject requestObject = new RequestObject(requestURL);
                    Class c = Class.forName(servletClass);
                    LoginServlet servlet = (LoginServlet) c.newInstance();
                    servlet.service(requestObject,responseObj);
                }else{
                    StringBuffer html = new StringBuffer();
                    html.append("HTTP/1.1 404 OK\n");
                    html.append("Content-Type:text/html;charset=utf-8\n\n");
                    html.append("<html>");
                    html.append("<head>");
                    html.append("<body>404</body>");
                    html.append("</head>");
                    html.append("</html>");
                    out.print(html);
                }

            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(clientScoket!=null){
                try {
                    clientScoket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseStaticPage(String requestURL, PrintWriter out) {
        String htmlPath = requestURL.substring(1);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(htmlPath));
                StringBuffer html = new StringBuffer();
            html.append("HTTP/1.1 200 OK\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            String temp = null;
            while((temp =br.readLine())!=null){
                html.append(temp);
            }
            out.print(html);
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer html = new StringBuffer();
            html.append("HTTP/1.1 404 OK\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<body>2342343</body>");
            html.append("</head>");
            html.append("</html>");
            out.print(html);
        }
    }
}
