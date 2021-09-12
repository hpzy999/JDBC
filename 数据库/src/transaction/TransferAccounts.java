package transaction;

import org.junit.Test;
import util.JDBCUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * 转账操作演示事务
 * @author 黄富贵
 * 针对于user表
 *
 * 数据库事务
 *   事务：一组逻辑操作单元，时数据从一种状态到另一种状态
 *       一组逻辑操作单元，指一个或多个DML操作
 */
//转账类
public class TransferAccounts extends JDBCUtil{
    Scanner in = new Scanner(System.in);
    /*
       测试转账出错
       不考虑事务机制
     */
    @Test
    public void test1(){
        String sql1 = "update sqlwork.user set balance = balance - ? where uid = ?";
        String sql2 = "update sqlwork.user set balance = balance + ? where uid = ?";
        System.out.println("请输入你的id：");
        String id1 = in.next();
        System.out.println("请输入你转账的id：");
        String id2 = in.next();
        System.out.println("请输入你要转账的金额:");
        BigDecimal b1 = in.nextBigDecimal();
        JDBCUtil.update(sql1,b1,id1);
        //演示转账失败
        if (true){
            throw new RuntimeException("网络异常");
        }
        JDBCUtil.update(sql2,b1,id2);
        System.out.println("转账成功");
    }

    /*
      考虑数据库事务
     */
    @Test
    public void test2(){
        String sql1 = "update sqlwork.user set balance = balance - ? where uid = ?";
        String sql2 = "update sqlwork.user set balance = balance + ? where uid = ?";
        System.out.println("请输入你的id：");
        String id1 = in.next();
        System.out.println("请输入你转账的id：");
        String id2 = in.next();
        System.out.println("请输入你要转账的金额:");
        BigDecimal balance = in.nextBigDecimal();


        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            update(con,sql1,balance,id1);
            //演示转账失败
//            System.out.println(10 / 0);
            if (balance.doubleValue() > 5000){
                throw new RuntimeException("转账金额不能大于5000");
            }
            update(con,sql2,balance,id2);
            System.out.println("转账成功");
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            try {
                if (con != null)
                con.rollback();
                System.out.println("操作已取消...");
//                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            JDBCUtil.closeResoure(con,null);
        }

    }

    public static void update(Connection con,String sql,Object ...args){
        PreparedStatement ps = null;
        try {
            //预编译sql语句，获取PrepareStatement的实例
            ps = con.prepareStatement(sql);
            //自动提交改为false
            con.setAutoCommit(false);
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

}
