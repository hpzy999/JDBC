package DatabaseClass;

//课程表类
public class Course {
    private String Cid;
    private String Cname;
    private String Tid;
    public Course(){}

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public Course(String cid, String cname, String tid) {
        Cid = cid;
        Cname = cname;
        Tid = tid;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Cid='" + Cid + '\'' +
                ", Cname='" + Cname + '\'' +
                ", Tid='" + Tid + '\'' +
                '}';
    }
}
