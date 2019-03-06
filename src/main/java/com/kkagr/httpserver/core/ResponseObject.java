package com.kkagr.httpserver.core;

import javax.kkagr.servlet.ServletResopnse;
import java.io.PrintWriter;

public class ResponseObject implements ServletResopnse {
    public PrintWriter out;
    public void setWriter(PrintWriter out){
        this.out = out;
    }
    public PrintWriter getWriter(){
        return out;
    }
}
