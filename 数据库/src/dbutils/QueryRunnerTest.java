package dbutils;

import DatabaseClass.Student;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * comment-dbutils 是apache提供的一套开源的jdbc工具类库，封装了针对于数据库的增删改查操作
 */
public class QueryRunnerTest {

    /*
      测试向student表中插入一条记录
     */
    @Test
    public void testInsert(){
        Properties prop = new Properties();
        InputStream in = QueryRunnerTest.class.getClassLoader().getResourceAsStream("druid.properties");
        Connection conn = null;
        try {
            prop.load(in);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            conn = ds.getConnection();
            QueryRunner run = new QueryRunner();
            String sql = "insert into student(Sid,Sname,Sage,Ssex) values(?,?,?,?)";
            int update = run.update(conn, sql, 520, "蔡徐坤", "1979-09-19", "男");
            System.out.println("添加了"+update+"条记录");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /*
      测试查询student表中Sid=07的学生信息
      BeanHandler是ResultSetHandler接口的实现类，封装了表中的一条记录
     */
    @Test
    public void testQuery(){
        Properties prop = new Properties();
        InputStream in = QueryRunnerTest.class.getClassLoader().getResourceAsStream("druid.properties");
        Connection conn = null;
        try {
            prop.load(in);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            conn = ds.getConnection();
            QueryRunner run = new QueryRunner();
            String sql = "select Sid,Sname,Sage,Ssex from student where Sid = ?";
            BeanHandler<Student> handler = new BeanHandler<>(Student.class);
            Student query = run.query(conn, sql, handler, "07");
            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * BeanListHandler查询结果是多条记录
     */
    @Test
    public void testQuery2(){
        Properties prop = new Properties();
        InputStream in = QueryRunnerTest.class.getClassLoader().getResourceAsStream("druid.properties");
        Connection conn = null;
        try {
            prop.load(in);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            conn = ds.getConnection();
            QueryRunner run = new QueryRunner();
            String sql = "select Sid,Sname,Sage,Ssex from student ";
            BeanListHandler<Student> handler = new BeanListHandler<>(Student.class);
            List<Student> query = run.query(conn, sql, handler);
            query.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * MapListHandler查询结果是多条记录
     */
    @Test
    public void testQuery3(){
        Properties prop = new Properties();
        InputStream in = QueryRunnerTest.class.getClassLoader().getResourceAsStream("druid.properties");
        Connection conn = null;
        try {
            prop.load(in);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            conn = ds.getConnection();
            QueryRunner run = new QueryRunner();
            String sql = "select Sid,Sname,Sage,Ssex from student ";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> query = run.query(conn, sql, handler);
            query.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * MapHandler查询结果是一条记录
     */
    @Test
    public void testQuery4(){
        Properties prop = new Properties();
        InputStream in = QueryRunnerTest.class.getClassLoader().getResourceAsStream("druid.properties");
        Connection conn = null;
        try {
            prop.load(in);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            conn = ds.getConnection();
            QueryRunner run = new QueryRunner();
            String sql = "select Sid,Sname,Sage,Ssex from student where Sid = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> query = run.query(conn, sql, handler, "07");
            System.out.print("{");
            for (Map.Entry<String,Object> entry:query.entrySet()){
                System.out.print(entry.getKey()+"="+entry.getValue()+",");
            }
            System.out.println("}");

//            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
