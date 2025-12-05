package com.group2.studentportal.backend.models;

import java.sql.Date;

public class Student extends Account {

    private String studentNo;
    private String courseCode;
    private String status;
    private String yearLevel;

    public Student() {
        super();
    }

    public Student(String accountNo, String firstName, String lastName, String middleName,
                   String password, String email, String contactNo, String address,
                   String gender, Date birthday, String role,
                   String studentNo, String courseCode, String status, String yearLevel) {

        super(accountNo, firstName, lastName, middleName, password, email,
                contactNo, address, gender, birthday, role);

        this.studentNo = studentNo;
        this.courseCode = courseCode;
        this.status = status;
        this.yearLevel = yearLevel;
    }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getYearLevel() { return yearLevel; }
    public void setYearLevel(String yearLevel) { this.yearLevel = yearLevel; }
}