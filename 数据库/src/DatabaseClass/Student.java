package DatabaseClass;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.PrimitiveIterator;

/**
 * ORM编程思想(Object relational mapping)  对象关系映射
 * 一个数据表对应一个类
 * 表中一条记录对应一个对象
 * 各个字段对应属性
 *
 * 对应数据库sqlwork.student表
 * @author 黄富贵
 */

//学生表类
public class Student {
    private String Sid;
    private String Sname;
    private Date Sage;//出生日期 yy-MM-dd hh-mm-ss
    private String Ssex;//性别
    private byte[] photo;

    public Student(){

    }

    public Student(String sid, String sname, Date sage, String ssex) {
        Sid = sid;
        Sname = sname;
        Sage = sage;
        Ssex = ssex;
    }

    public Student(String sid, String sname, Date sage, String ssex, byte[] photo) {
        Sid = sid;
        Sname = sname;
        Sage = sage;
        Ssex = ssex;
        this.photo = photo;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public Date getSage() {
        return Sage;
    }

    public void setSage(Date sage) {
        Sage = sage;
    }

    public String getSsex() {
        return Ssex;
    }

    public void setSsex(String ssex) {
        Ssex = ssex;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Sid='" + Sid + '\'' +
                ", Sname='" + Sname + '\'' +
                ", Sage=" + Sage +
                ", Ssex='" + Ssex + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
