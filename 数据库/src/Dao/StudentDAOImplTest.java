package Dao;

import DatabaseClass.Student;
import org.junit.Test;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDAOImplTest {
    private final StudentDAOImpl studentDAO = new StudentDAOImpl();
    Connection conn;
    {
        try {
            conn = JDBCUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert() {
        Student student = new Student("520", "火箭筒", new Date(1999,06,06), "女");
        studentDAO.insert(conn,student);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deleteById() {
        studentDAO.deleteById(conn,"520");
    }

    @Test
    public void update() {
        studentDAO.update(conn,new Student("09","陈富贵",new Date(2021, 5,6),"男"));
    }

    @Test
    public void getStudentBySid() {
        Student studentBySid = studentDAO.getStudentBySid(conn, "07");
        System.out.println(studentBySid);
    }

    @Test
    public void getAll() {
        List<Student> all = studentDAO.getAll(conn);
        all.forEach(System.out::println);
    }

    @Test
    public void getCount() {
        System.out.println(studentDAO.getCount(conn));
    }

    @Test
    public void getMaxBirth() {
        System.out.println(studentDAO.getMaxBirth(conn));
    }
    @Test
    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}