package com.HYK.servlet;

import com.HYK.exception.AppException;
import com.HYK.model.JSONResponse;
import com.HYK.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //请求体的编码设置
        req.setCharacterEncoding("UTF-8");
        //响应体编码设置
        resp.setCharacterEncoding("UTF-8");
        //设置响应体数据类型
        resp.setContentType("application/json");
        //TODO
        //req.getServletPath();
        JSONResponse json = new JSONResponse();
        try{
            //调用子类重写方法
            Object data = process(req,resp);
            //子类process 方法执行完成 业务成功
            json.setSuccess(true);
            json.setData(data);
        }catch (Exception e){
            //JDBC
            //JSON
            e.printStackTrace();
            String code = "unknown";
            String s = "服务器未知错误";
            if(e instanceof AppException){
                code  = ((AppException)e).getCode();
                s =  e.getMessage();
            }
            json.setCode(code);
            json.setMessage(s);

        }
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(json));
        pw.flush();
        pw.close();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;


}
