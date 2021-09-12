package Dao;

import util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装了对数据库的通用操作
 */

public class BaseDao {
    /**
     * 通用的增删改操作
     * @param  sql
     * @param args
     */
    public static void update(Connection con,String sql,Object ...args){
        PreparedStatement ps = null;
        try {
            //获取数据库连接
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
            JDBCUtil.closeResoure(null,ps);
        }
    }

    /**
     *针对不同表的查询结果是单条记录的查询操作
     *
     */
    public static <T> T selectReturnInstance(Connection con,Class<T> clazz ,String sql,Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
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
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(null,ps,rs);
        }
        return null;
    }

    /**
     *针对不同的表的查询结果有多条数据的通用查询
     * @return List
     */
    public static <T> List<T> selectReturnList(Connection conn,Class<T> clazz, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
            JDBCUtil.closeResoure(null,ps,rs);
        }
        return null;
    }

    /*
       查询特殊值的通用方法
     */

    public static <E> E getValue(Connection con,String sql,Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //预编译sql语句
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(null,ps,rs);
        }
        return null;
    }
}
