package jdbc;


import DatabaseClass.Scores;
import DatabaseClass.Student;

import org.junit.Test;
import util.JDBCUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对不同表的通用查询操作
 * @author 黄富贵
 */

public class PrepareStatementSelect {


    @Test
    public void test2(){
        //查询成绩低于60分的记录，按学生id分组
        String sql = "select Sid Studentid,Cid Classid,score from sc where score < ? group by Studentid";
        List<Scores> forList = getForList(Scores.class, sql, 60);
        forList.forEach(System.out::println);

        String sql1 = "select Sid Studentid,Cid Classid,score from sc where score < ? group by Studentid";
        List<Scores> list = getSelectField(Scores.class, sql1, 60);
        forList.forEach(System.out::println);
    }

    @Test
    public void test3() {
        try {
            String sql = "select Sid,Sname,Sage,Ssex,photo from student where Sid = ?";
            List<Student> list = getForList(Student.class, sql, "01");
            OutputStream os = new FileOutputStream("D:\\desktop\\a.jpg");
            os.write(list.get(0).getPhoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *针对不同表的查询结果是单条记录的查询操作
     *
     */
    public <T> T prepareStatementSelect(Class<T> clazz ,String sql,Object ...args) {
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
            JDBCUtil.closeResoure(conn,ps,rs);
        }
        return null;
    }

    /**
     *针对不同的表的查询结果有多条数据的通用查询
     * @return List
     */
    public <T> List<T> getForList(Class<T> clazz,String sql,Object ...args){
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

    /**
     * 通过类的属性与结果集的列的别名的equals方法，然后再赋值
     * @param clazz
     * @param sql
     * @param args
     * @param <T> T
     * @return List
     */
    public <T> List<T> getSelectField(Class<T> clazz,String sql,Object ...args){
        try {
            Connection coon = JDBCUtil.getConnection();
            PreparedStatement ps = coon.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            ResultSet rs = ps.executeQuery();
            List<T> arr = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()){
                T t = clazz.newInstance();
                Field[] Fields = clazz.getDeclaredFields();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    for (int j = 0; j < Fields.length; j++) {
                        if (columnLabel.equals(Fields[i].getName())){
                            Fields[i].setAccessible(true);
                            Fields[i].set(t,value);
                        }
                    }
                }
                arr.add(t);
            }
            return arr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
