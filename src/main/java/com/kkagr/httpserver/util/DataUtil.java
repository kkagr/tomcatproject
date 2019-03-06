package com.kkagr.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    public static String NowDataStr(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        return dateFormat.format(new Date());
    }
}
