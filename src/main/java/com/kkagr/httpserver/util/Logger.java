package com.kkagr.httpserver.util;

public class Logger {
    private Logger(){

    }
    public static void log(String msg){
        String nowTimeStr =DataUtil.NowDataStr();
        System.out.println("[INFO]" +nowTimeStr+" "+msg );
    }
}
