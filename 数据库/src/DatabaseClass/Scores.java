package DatabaseClass;

import java.math.BigDecimal;

/**
 * 成绩表的类
 * ORM对象关系映射编程思想
 * 对应sqlwork.sc表
 */
public class Scores {
    private String Studentid;
    private String Classid;
    private BigDecimal score;

    public Scores(){}

    public Scores(String studentid, String classid, BigDecimal score) {
        Studentid = studentid;
        Classid = classid;
        this.score = score;
    }

    public String getStudentid() {
        return Studentid;
    }

    public void setStudentid(String studentid) {
        Studentid = studentid;
    }

    public String getClassid() {
        return Classid;
    }

    public void setClassid(String classid) {
        Classid = classid;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "Studentid='" + Studentid + '\'' +
                ", Classid='" + Classid + '\'' +
                ", score=" + score +
                '}';
    }
}
