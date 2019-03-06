package com.kkagr.httpserver.server;

import javax.kkagr.servlet.Servlet;
import javax.kkagr.servlet.ServletRequest;
import javax.kkagr.servlet.ServletResopnse;

public class LoginServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResopnse resopnse) {
        String name = request.getParamterValue("name");
        StringBuffer html = new StringBuffer();
        html.append("HTTP/1.1 200 OK\n");
        html.append("Content-Type:text/html;charset=utf-8\n\n");
        html.append("<html>");
        html.append("<head>");
        html.append("<body>");
        html.append(name);
        html.append("</body>");
        html.append("</head>");
        html.append("</html>");
        resopnse.getWriter().print(html);
    }
}
