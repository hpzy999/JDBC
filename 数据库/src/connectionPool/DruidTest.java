package connectionPool;

import Dao.StudentDAOImpl;
import DatabaseClass.Student;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 测试druid数据库连接池
 *
 */

public class DruidTest {

    @Test
    public void test1(){
        Connection conn = getConnection();
        System.out.println(conn);
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        List<Student> all = studentDAO.getAll(conn);
        all.forEach(System.out::println);
        try {
            if (conn!= null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection(){
        Properties prop = new Properties();
        InputStream is = DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(is);
            DataSource ds = DruidDataSourceFactory.createDataSource(prop);
            Connection conn = ds.getConnection();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
