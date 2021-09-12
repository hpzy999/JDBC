package jdbc;


import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 测试JDBC
 */
public class ConnetionTest {

    @Test
    //方式一
    public void testconnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        String ur1 = "jdbc:mysql://localhost:3306/exam?useUnicode=true&amp";
        Properties info =new Properties();
        info.setProperty("user","root");
        info.setProperty("password","991106");
        Connection con = driver.connect(ur1, info);
        System.out.println(con);
    }

    //方式二:对方式一的迭代：使用反射:在程序中不适用第三方的API，使得程序具有可移植性
    @Test
    public void ConnectionTest2() throws Exception {
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver  driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/sqlwork";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","991106");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
    //方式三：DriverManager ：驱动管理器
    @Test
    public void CnnectionTest3() throws Exception {
        //通过反射获取运行时类的对象
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/sqlwork";
        String user = "root";
        String password = "991106";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式四：优化方式三
    @Test
    public void CnnectionTest4() throws Exception {
        String url = "jdbc:mysql://localhost:3306/sqlwork";
        String user = "root";
        String password = "991106";

        //通过反射获取运行时类的对象
        Class.forName("com.mysql.cj.jdbc.Driver");

        //如下代码可以省略，因为在MySQL的Driver实现类中声明了静态代码块去实现注册驱动的操作
//        Driver driver = (Driver) clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式五：将数据库连接需要的四个基本信息声明在配置文件中，通过读取配置文件获取连接需要的信息
    @Test
    public void ConnectionTest5() throws Exception{
        InputStream input = ConnetionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(input);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String driverclass = pros.getProperty("driverclass");
        String url = pros.getProperty("url");

        //加载驱动
        Class.forName(driverclass);
        //获取连接
        Connection con = DriverManager.getConnection(url, user, password);
        System.out.println(con);

    }


}
