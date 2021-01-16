package com.HYK.servlet;

import com.HYK.DAO.ArticleDAO;
import com.HYK.model.Article;
import com.HYK.model.User;
import com.HYK.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");

        InputStream is = req.getInputStream();
        Article a = JSONUtil.deserialize(is,Article.class);
        a.setUserId(user.getId());
        int num = ArticleDAO.insert(a);


        return null;
    }
}
