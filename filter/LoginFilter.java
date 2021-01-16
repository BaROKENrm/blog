//package com.HYK.filter;
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
