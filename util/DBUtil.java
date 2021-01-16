package com.HYK.util;

import com.HYK.exception.AppException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/servlet_blog?user=root&password=huyikuan123&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static DataSource DS = new MysqlDataSource();

    /**
     *
     */
    static{
        ((MysqlDataSource) DS).setUrl(URL);
    }

    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            //抛自定义异常
            throw new AppException("DB001","获取数据库连接失败",e);
        }
    }


    public static void close(Connection c, PreparedStatement s){
        close(c,s,null);
    }

    public static void close(Connection c, PreparedStatement s, ResultSet r){
        try {
            if(c != null){
                c.close();
            }
            if(s != null){
                s.close();
            }
            if(r != null){
                r.close();
            }
        } catch (SQLException e) {
            throw new AppException("DB002","数据库释放资源出错",e);
        }
    }

}
