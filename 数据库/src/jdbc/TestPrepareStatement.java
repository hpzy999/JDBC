package jdbc;

import util.JDBCUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 使用prepareStatement进行数据库的增删改
 * @author 黄富贵
 */

public class TestPrepareStatement {

    @Test
    public void Test(){
        String sql = "update student set photo = ? where Sid = ?";
        InputStream is = null;
        try {
            is = TestPrepareStatement.class.getResourceAsStream("/images/7.jpg");
            update(sql,is,"07");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    /*
     通用增删改操作
     */
    public static void update(String sql,Object ...args){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //获取数据库连接
            con = JDBCUtil.getConnection();
            //预编译sql语句，获取PrepareStatement的实例
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源连接
            JDBCUtil.closeResoure(con,ps);
        }
    }

    //向sqlwork.student中插入一条记录
    @Test
    public void testInsert(){
        try {
            InputStream is = TestPrepareStatement.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties ppts = new Properties();
            ppts.load(is);

            String user = ppts.getProperty("user");
            String password = ppts.getProperty("password");
            String url = ppts.getProperty("url");
            String drivers = ppts.getProperty("driverclass");

            Class.forName(drivers);

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println(con);

            String sql = "insert into `sqlwork`.`student`(Sid,Sname,Sage,Ssex) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,"10");
            ps.setString(2,"元华");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            java.util.Date parse = sdf.parse("1998-07-21 00-00-00");
            ps.setDate(3,new Date(parse.getTime()));
            ps.setString(4,"男");

            //执行操作
            ps.execute();
            //关闭资源
            ps.close();
            con.close();
            if (is != null) {
                is.close();
            }
        } catch (IOException | SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //获取数据库连接
            con = JDBCUtil.getConnection();
            //预编译sql语句，返回prepareStatement的实例
            String sql = "update sqlwork.student set Sname = ? where Sid = ? ";
             ps = con.prepareStatement(sql);
            //填充占位符
            ps.setString(1,"马冬梅");
            ps.setString(2,"10");
            //执行
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            JDBCUtil.closeResoure(con,ps);
        }
    }

    /*
    删除sqlwork.student中姓名为马冬梅的记录
     */
    @Test
    public void testDelete(){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtil.getConnection();

            String sql = "delete from sqlwork.student where Sname = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1,"马冬梅");

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(con,ps);
        }

    }


}
