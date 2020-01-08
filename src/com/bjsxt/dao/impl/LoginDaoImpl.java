package com.bjsxt.dao.impl;

import com.bjsxt.dao.LoginDao;
import com.bjsxt.pojo.User;

import java.sql.*;

public class LoginDaoImpl implements LoginDao {

    @Override
    public User checkLoginDao(String uname, String pwd) {
        //声明Jdbc对象
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //声明数据存储对象
        User u = null;

        //加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wollo2","root","123456");
            //获取sql命令
            String sql = "select * from t_user where uname=? and pwd=?";
            //创建sql命令对象
            ps = conn.prepareStatement(sql);
            //给占位符赋值
            ps.setString(1,uname);
            ps.setString(2,pwd);
            //执行
            rs = ps.executeQuery();
            //遍历执行结果
            while (rs.next()){
                u = new User();
                u.setUid(rs.getInt("uid"));
                u.setPwd(rs.getString("pwd"));
                u.setUname(rs.getString("uname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    return u;
    }

    @Override
    public User checkUidDao(String uid) {
        //声明Jdbc对象
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        //声明数据存储对象
        User u=null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接对象
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/wollo2","root","123456");
            //创建Sql命令
            String sql="select * from t_user where uid=?";
            //创建Sql命令对象
            ps=conn.prepareStatement(sql);
            //给占位符赋值
            ps.setString(1, uid);
            //执行
            rs=ps.executeQuery();
            //遍历执行结果
            while(rs.next()){
                u=new User();
                u.setUid(rs.getInt("uid"));
                u.setUname(rs.getString("uname"));
                u.setPwd(rs.getString("pwd"));
            }
            //关闭资源
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //返回
        return u;
    }
}
