package blob;

import DatabaseClass.Student;
import jdbc.TestPrepareStatement;
import org.junit.jupiter.api.Test;
import util.JDBCUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static jdbc.TestPrepareStatement.update;

public class TestBlob {

    /**
     * 读取sqlwork.student表中的图片到桌面中
     */
    @Test
    public void test1(){
        try {
            String sql = "select Sid,Sname,Sage,Ssex,photo from student where Sid = ?";
            List<Student> list = JDBCUtil.select(Student.class, sql, "05");
            OutputStream os = new FileOutputStream("D:\\desktop\\4.jpg");
            os.write(list.get(0).getPhoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("............");
    }


    /**
     * 修改student表中的图片
     */
    @Test
    public void test2() throws Exception {
        Class<?> jdbcUtil = Class.forName("JDBCUtil");
        JDBCUtil obj = (JDBCUtil) jdbcUtil.newInstance();
        String sql = "update student set photo = ? where Sid = ?";
        InputStream is = null;
        try {
            is = TestPrepareStatement.class.getResourceAsStream("/images/4.jpg");
            obj.update(sql,is,"05");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
