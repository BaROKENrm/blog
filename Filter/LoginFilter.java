package com.HYK.filter;
//
//import com.HYK.model.JSONResponse;
//import com.HYK.util.JSONUtil;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@WebFilter("/*")
//public class LoginFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        String servletPath = req.getServletPath();
//        //不需要登录允许访问
//        if(servletPath.startsWith("/js/") || servletPath.startsWith("/static/") || servletPath.startsWith("/view/") || servletPath.equals("/login/")){
//            filterChain.doFilter(request,response);
//        }
//        else{
//            HttpSession session = req.getSession(false);
//            if(session == null || session.getAttribute("user") == null){
//                if(servletPath.startsWith("/jsp/")){
//                    resp.sendRedirect(basePath(req) + "/view/login.html");
//                }
//                else{
//                    //返回401
//                    resp.setStatus(401);
//                    //响应体编码设置
//                    resp.setCharacterEncoding("UTF-8");
//                    //设置响应体数据类型
//                    resp.setContentType("application/json");
//                    JSONResponse json = new JSONResponse();
//                    json.setCode("LOG000");
//                    json.setMessage("用户没登录，不允许访问");
//                    PrintWriter pw = resp.getWriter();
//                    pw.println(JSONUtil.serialize(json));
//                    pw.flush();
//                    pw.close();
//                }
//            }
//            else{
//                //敏感资源 但已登录 继续执行
//                filterChain.doFilter(request,response);
//            }
//        }
//    }
//
//    public static String basePath(HttpServletRequest req){
//        String schema = req.getScheme();//http
//        String host = req.getServerName();//主机ip或域
//        int port = req.getServerPort();//服务器端口号
//        String contextPath = req.getContextPath();//应用上下文路径
//        return schema + "://" + host + ":" + port +contextPath;
//
//    }
//}

import com.HYK.model.JSONResponse;
import com.HYK.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 配置用户统一会话管理过滤器,匹配所有请求路径
 * 服务端资源: /login 不需要校验session，其他都需要校验,如果不通过，返回401，相应内容随便
 * 前端资源: /jsp/需要校验session，不通过重定向到登陆页面
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 每次http请求匹配到过滤器路径时就会执行过滤器的doFilter方法
     * 如果向下执行，调用filterChain,doFilter(request,response)
     * 否则自行处理相应内容
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();//获取当前请求服务路径
        //不需要登录允许:向下执行
        if (path.startsWith("/js/") || path.startsWith("/static/")
                || path.startsWith("/view/") || path.equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            //获取session对象,没有返回null
            HttpSession session = req.getSession(false);
            //验证用户是否登录,如果没有登录,需要根据前端或者后端做不同处理
            if (session == null || session.getAttribute("user") == null) {
                //前端重定向到登录页
                if (path.startsWith("/jsp/")) {
                    resp.sendRedirect(basepath(req) + "/view/login.html");
                } else {
                    resp.setCharacterEncoding("UTF-8");
                   //req.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    //后端返回401状态码
                    resp.setStatus(401);
                    //返回统一的json数据格式
                    JSONResponse json = new JSONResponse();
                    json.setCode("login000");
                    json.setMessage("用户未登录");
                    PrintWriter pw = response.getWriter();
                    pw.println(JSONUtil.serialize(json));
                    pw.flush();
                    pw.close();
                }
            } else {
                //敏感资源，已经登陆,继续执行
                filterChain.doFilter(request, response);
            }
        }

    }

    /**
     * 根据http请求,动态获取访问你路径（服务路径之前的部分）
     *
     * @param req
     * @return
     */

    public static String basepath(HttpServletRequest req) {
        String schema = req.getScheme();//http
        String host = req.getServerName();//主机ip或域名
        int port = req.getServerPort();//服务器端口号
        String contextPath = req.getContextPath();//获取应用上下文路径
        return schema + "://" + host + ":" + port + contextPath;
    }

    @Override
    public void destroy() {

    }
}