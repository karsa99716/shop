package com.gjc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        filterName = "EncodingFilter",
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD
        },
        urlPatterns = { "/*" }
)
public class EncodingFilter implements Filter {
    public EncodingFilter(){
    }
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//处理post请求
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equalsIgnoreCase("get")){
            EncodingServletRequest esr= new EncodingServletRequest(req);
            chain.doFilter(esr,response);
        }else if (req.getMethod().equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
            chain.doFilter(request,response);
        }else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
