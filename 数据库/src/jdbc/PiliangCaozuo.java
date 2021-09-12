package jdbc;

import org.junit.Test;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 */

public class PiliangCaozuo {


    @Test
    public void test1(){
        String sql = "insert into sqlwork.counts values(?)";
        manyInsert(sql);
    }

    /**
     * 针对insert的批量操作
     */
    public void manyInsert(String sql){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtil.getConnection();
            ps = con.prepareStatement(sql);
            //设置自动提交为false
//            con.setAutoCommit(true);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 30000; i++) {
                ps.setObject(1,"name_"+i);
                //攒sql
                ps.addBatch();
                if (i % 1000 ==0){
                    //执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }
            }
            //提交数据
//            con.commit();
            long end = System.currentTimeMillis();
            System.out.println("运行时长:"+(end - start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResoure(con,ps);
        }
    }
}
