package Dao;

import DatabaseClass.Student;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/*
 * 此接口用于规范针对于customers表的常用操作
 */

public interface StudentDAO {
    /**
     *
     * @Description 将cust对象添加到数据库中
     * @author shkstart
     * @date 上午11:00:27
     * @param conn
     * @param stu
     */
    void insert(Connection conn, Student stu);
    /**
     *
     * @Description 针对指定的id，删除表中的一条记录
     * @author shkstart
     * @date 上午11:01:07
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,String id);
    /**
     *
     * @Description 针对内存中的cust对象，去修改数据表中指定的记录
     * @author shkstart
     * @date 上午11:02:14
     * @param conn
     * @param stu
     */
    void update(Connection conn,Student stu);
    /**
     *
     * @Description 针对指定的id查询得到对应的Customer对象
     * @author shkstart
     * @date 上午11:02:59
     * @param conn
     * @param id
     */
    Student getStudentBySid(Connection conn,String id);
    /**
     *
     * @Description 查询表中的所有记录构成的集合
     * @author shkstart
     * @date 上午11:03:50
     * @param conn
     * @return
     */
    List<Student> getAll(Connection conn);
    /**
     *
     * @Description 返回数据表中的数据的条目数
     * @author shkstart
     * @date 上午11:04:44
     * @param conn
     * @return
     */
    Long getCount(Connection conn);

    /**
     *
     * @Description 返回数据表中最大的生日
     * @author shkstart
     * @date 上午11:05:33
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
