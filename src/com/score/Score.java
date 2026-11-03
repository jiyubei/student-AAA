package com.score;

import java.io.Serializable;
import java.util.Date;

/**
 * 成绩实体类
 */
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;           // 主键ID
    private String sno;           // 学号
    private String studentName;   // 学生姓名
    private String courseName;    // 课程名称
    private Double score;         // 成绩（0-100）
    private Date createTime;      // 录入时间

    public Score() {
    }

    public Score(Integer id, String sno, String studentName, String courseName, Double score) {
        this.id = id;
        this.sno = sno;
        this.studentName = studentName;
        this.courseName = courseName;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", sno='" + sno + '\'' +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }
}
