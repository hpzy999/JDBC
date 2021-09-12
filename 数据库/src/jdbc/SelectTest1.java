package jdbc;

import DatabaseClass.Scores;
import DatabaseClass.Student;
import org.junit.jupiter.api.Test;
import util.JDBCUtil;
import java.lang.reflect.Field;
import java.sql.*;

public class SelectTest1 {

    public static void main(String[] args) {
        String sql = "select Sid,Sname,Sage,Ssex from sqlwork.student where Sid = ?";
        Student student = studentForSelect(sql, "07");
        System.out.println(student);

        String sql1 = "select *from sqlwork.student where Sname = ?";
        Student student1 = studentForSelect(sql1, "黄富贵");
        System.out.println(student1);
    }
    @Test
    public void testSelectScores() throws ClassNotFoundException {
        String sql = "select Sid Studentid,Cid Classid,score from sqlwork.sc where Sid = ?";
        Scores scores = scoresForSelect(sql, "02");
        System.out.println(scores);
    }

    /**
     * 针对student表的通用查询操作
     */
    public static Student studentForSelect(String sql,Object ...args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            con = JDBCUtil.getConnection();
            //预编译sql语句
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行查询
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //通过ResultSetMetaData 获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            if (rs.next()){
                Student student = new Student();
                //处理结果集中一行数据的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取列的值
                    Object value = rs.getObject(i+1);
                    //获取列的名称
                    String columnName = metaData.getColumnName(i + 1);
                    //获取列的别名
//                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //给student对象指定的columnLabel属性，赋值为value，通过反射
                    Field field = Student.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(student,value);
                }
                return student;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtil.closeResoure(con,ps,rs);
        }
        return null;
    }

    /**
     * 针对成绩表的通用查询
     * 针对于表的字段名与类的属性名不一致的情况：
     * 使用结果集元数据时，用getColumnLabel替换getColumnName获取列的别名
     * 若没有别名，getColumnLabel依然可以获取列的名称
     */
    public Scores scoresForSelect(String sql,Object ...args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            con = JDBCUtil.getConnection();
            //预编译sql语句
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行查询
            rs = ps.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取结果集的列数
            int columnCount = metaData.getColumnCount();

            while (rs.next()){
                Scores scores = new Scores();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    //获取列的名称
                    String columnLabel = metaData.getColumnLabel(i+1);
                    Field field = Scores.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(scores,value);
                }
                return scores;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(con,ps,rs);
        }
        return null;
    }

    /**
     * 针对student表的固定查询操作
     */
    @Test
    @SuppressWarnings("all")
    public void  testSelect1(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取数据库连接
            con = JDBCUtil.getConnection();
            //预编译sql语句
            String sql = "select Sid,Sname,Sage,Ssex from sqlwork.student where Sid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,"07");
            //获取结果集
            rs = ps.executeQuery();
            //处理结果集
            if (rs.next()) {
                //获取当前结果集的每一个字段
                String id = rs.getString(1);
                String name = rs.getString(2);
                Date birth = rs.getDate(3);
                String sex = rs.getString(4);

                //方式一:不推荐
//            System.out.println("id="+id+",name="+name+",birth="+birth+",sex="+sex);
                //方式二：不推荐
//            Object[] obj = new Object[]{id,name,birth,sex};
                //方式三
                Student student = new Student(id,name,birth,sex);
                System.out.println(student);
            }
        }catch(Exception e){
                e.printStackTrace();
        } finally {
            JDBCUtil.closeResoure(con,ps);
        }
    }
}
