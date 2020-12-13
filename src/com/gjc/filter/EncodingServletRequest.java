package com.gjc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

public class EncodingServletRequest extends HttpServletRequestWrapper {
    private HttpServletRequest req;
    public EncodingServletRequest(HttpServletRequest request){
        super(request);
        this.req = request;
    }

    @Override
    public String getParameter(String name) {
        String value = req.getParameter(name);
        try {
            if (value!=null){
                value = new String(value.getBytes("iso-8859-1"),"utf-8");//处理中文乱码
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
