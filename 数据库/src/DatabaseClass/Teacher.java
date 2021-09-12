package DatabaseClass;

//Teacher老师表类
public class Teacher {
    private String Tid;
    private String name;
    public Teacher(){}

    public Teacher(String tid, String name) {
        Tid = tid;
        this.name = name;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Tid='" + Tid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
