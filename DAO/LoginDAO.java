package com.HYK.DAO;

import com.HYK.exception.AppException;
import com.HYK.model.User;
import com.HYK.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class LoginDAO {
    public static User query(String useranme){
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try{
            c = DBUtil.getConnection();
            String sql = "select id, username, password, nickname, sex, birthday, head from user where username = ?";
            ps = c.prepareStatement(sql);
            //设置占位符
            ps.setString(1,useranme);
            rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
                //设置user值
                user.setId(rs.getInt("id"));
                user.setUsername(useranme);
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setSex(rs.getBoolean("sex"));
                java.sql.Date birthday= rs.getDate("birthday");
                if(birthday != null){
                    user.setBirthday(new Date(birthday.getTime()));
                }

                user.setHead(rs.getString("head"));
            }
            return user;
        }catch(Exception e){
            throw new AppException("LOGIN001","查询用户操作出错",e);
        }finally{
            DBUtil.close(c,ps,rs);
        }
    }
}
