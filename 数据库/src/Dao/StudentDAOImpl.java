package Dao;


import DatabaseClass.Student;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

@SuppressWarnings("all")
public class StudentDAOImpl implements StudentDAO{
    @Override
    public void insert(Connection conn, Student stu) {
        String sql = "insert into student(Sid,Sname,Sage,Ssex)values(?,?,?,?)";
        BaseDao.update(conn,sql,stu.getSid(),stu.getSname(),stu.getSage(),stu.getSsex());
    }


    @Override
    public void deleteById(Connection conn, String id) {
        String sql = "delete from student where Sid = ?";
        BaseDao.update(conn,sql,id);
    }

    @Override
    public void update(Connection conn, Student stu) {
        String sql = "update student set Sname = ?,Sage = ?,Ssex = ? where Sid = ?";
        BaseDao.update(conn,sql,stu.getSname(),stu.getSage(),stu.getSsex(),stu.getSid());
    }

    @Override
    public Student getStudentBySid(Connection conn, String id) {
        String sql = "select Sid,Sname,Sage,Ssex from student where Sid = ?";
        Student student = BaseDao.selectReturnInstance(conn, Student.class, sql, id);
        return student;
    }

    @Override
    public List<Student> getAll(Connection conn) {
        String sql = "select Sid,Sname,Sage,Ssex from student";
        return BaseDao.selectReturnList(conn, Student.class, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from student";
        return BaseDao.getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(Sage) from student";
        return BaseDao.getValue(conn,sql);
    }
}
