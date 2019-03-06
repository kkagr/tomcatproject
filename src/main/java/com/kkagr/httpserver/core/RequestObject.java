package com.kkagr.httpserver.core;

import javax.kkagr.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestObject implements ServletRequest {

    public Map<String,String[]> parameterMap = new HashMap<>();
    public RequestObject(String requestURL){
        if(requestURL.contains("?")){
            String[] uriAndData = requestURL.split("[?]");
            if(uriAndData.length>1){
                String data = uriAndData[1];
                if(data.contains("&")){
                    String[] nameAndValues = data.split("&");
                    for(String nameAndValue:nameAndValues){
                        String[] nameAndValueAttr = nameAndValue.split("=");
                        if(parameterMap.containsKey(nameAndValueAttr[0])){
                            String[] values = parameterMap.get(nameAndValueAttr[0]);
                            String[] newValues = new String[values.length+1];
                            System.arraycopy(values,0,newValues,0,values.length);
                            if(nameAndValueAttr.length>1){
                                newValues[newValues.length-1] = nameAndValueAttr[1];
                            }else{
                                newValues[newValues.length-1] = "";
                            }
                            parameterMap.put(nameAndValueAttr[0],newValues);
                        }else{
                            if(nameAndValueAttr.length>1){
                                parameterMap.put(nameAndValueAttr[0],new String[] {nameAndValueAttr[1]});
                            }else{
                                parameterMap.put(nameAndValueAttr[0],new String[] {""});
                            }
                        }
                    }
                }else{
                    String[] nameAndValueAttr = data.split("=");
                    if(nameAndValueAttr.length>1){
                        parameterMap.put(nameAndValueAttr[0],new String[] {nameAndValueAttr[1]});
                    }else{
                        parameterMap.put(nameAndValueAttr[0],new String[] {""});
                    }
                }
            }
        }
    }

    public String getParamterValue(String key){
        String[] value = parameterMap.get(key);
        String v = null;
        if(value!=null && value.length>0){
            v = value[0];
        }
        return v;
    }
    public String[] getParamterValues(String key){
        return parameterMap.get(key);
    }
}
