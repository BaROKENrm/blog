package com.HYK.servlet;

import com.HYK.util.MyActionEnter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;

@WebServlet("/ueditor")
public class UEditorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = UEditorServlet.class.getClassLoader().getResource("config.json");
        String path = URLDecoder.decode(url.getPath(),"UTF-8");
        //框架提供的富文本编辑器上传功能
        MyActionEnter enter = new MyActionEnter(req,path);
        String exec = enter.exec();
        PrintWriter pw = resp.getWriter();
        pw.println(exec);
        pw.flush();
        pw.close();
    }
}
