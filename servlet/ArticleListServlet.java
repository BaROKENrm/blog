package com.HYK.servlet;

import com.HYK.DAO.ArticleDAO;
import com.HYK.exception.AppException;
import com.HYK.model.Article;
import com.HYK.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/articleList")
public class ArticleListServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);
        if(session == null){
            throw new AppException("ART002","用户没登录，不允许访问");
        }
        User user = (User)session.getAttribute("user");
        if (user == null){
            throw new AppException("ART003","请重新登录");
        }
        List<Article> articeList = ArticleDAO.queryByUserId(user.getId());
        return articeList;
    }
}
