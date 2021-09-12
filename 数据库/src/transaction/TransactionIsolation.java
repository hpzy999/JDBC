package transaction;

import org.junit.Test;
import util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static transaction.TransferAccounts.update;

/**
 * 事务的隔离级别
 *
 */

//事务隔离级别
public class TransactionIsolation {

    //事务一
    @Test
    public void testTransactionSelect() throws Exception{

        Connection conn = JDBCUtil.getConnection();
        //获取当前连接的隔离级别
        System.out.println(conn.getTransactionIsolation());
        //设置数据库的隔离级别：
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //取消自动提交数据
        conn.setAutoCommit(false);

        String sql = "select uid id,uname name,balance from user where uname = ?";
        User user = getInstance(conn, User.class, sql, "李雷雷");
        System.out.println(user);

    }

    //事务二
    @Test
    public void testTransactionUpdate() throws Exception{
        Connection conn = JDBCUtil.getConnection();

        //取消自动提交数据
        conn.setAutoCommit(false);
        String sql = "update user set balance = ? where uname = ?";
        update(conn, sql, 5000,"李雷雷");

        Thread.sleep(15000);
        System.out.println("修改结束");
    }

    //通用的查询操作，用于返回数据表中的一条记录（version 2.0：考虑上事务）
    public <T> T getInstance(Connection conn,Class<T> clazz,String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);

                    // 获取每个列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResoure(null, ps, rs);

        }

        return null;
    }
}
