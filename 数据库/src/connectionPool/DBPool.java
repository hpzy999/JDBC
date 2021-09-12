package connectionPool;

import DatabaseClass.Teacher;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 数据库连接池的使用
 */
public class DBPool {
    @Test
    public void test() {
        String sql = "select Tid ,Tname name from teacher";
        List<Teacher> query = query(Teacher.class, sql);
        query.forEach(System.out::println);

    }

    //通用查询
    public <T> List<T> query(Class<T> clazz,String sql, Object ...args) {
        Properties ppt = new Properties();
        InputStream is = DBPool.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            ppt.load(is);
            DataSource ds = DruidDataSourceFactory.createDataSource(ppt);
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
//            ps.setObject(1,"04");
            ResultSet rs = ps.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
