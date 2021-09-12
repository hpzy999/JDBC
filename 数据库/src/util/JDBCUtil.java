package util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 数据库操作的工具类
 * @author 黄富贵
 */

public class JDBCUtil {
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

    /**
     *针对不同的表的查询结果有多条数据的通用查询
     * @return List
     */
    public static <T> List<T> select(Class<T> clazz, String sql, Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            ArrayList<T> arr = new ArrayList<>();
            while (rs.next()){
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }
                arr.add(t);
            }
            return arr;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(conn,ps,rs);
        }
        return null;
    }

    /*
    获取数据库连接
     */
    public static Connection getConnection() throws Exception{
        //读取配置文件中的4个基本信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties ppts = new Properties();
        ppts.load(is);

        //获取属性信息
        String user = ppts.getProperty("user");
        String password = ppts.getProperty("password");
        String url = ppts.getProperty("url");
        String drivers = ppts.getProperty("driverclass");

        //加载驱动
        Class.forName(drivers);
        //获取连接
        Connection con = DriverManager.getConnection(url, user, password);
        if (con != null){
            System.out.println("数据库连接成功");
        }else {
            System.out.println("数据库连接失败");
        }
        return con;
    }

    /*
    关闭资源
     */
    public static void closeResoure(Connection con, PreparedStatement ps){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResoure(Connection con, PreparedStatement ps, ResultSet rs){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
