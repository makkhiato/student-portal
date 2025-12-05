package com.group2.studentportal.backend.models;

public class Course {
    private String courseCode;
    private String description;
    private String collegeCode; // Foreign Key
    private String status;

    public Course() {}

    public Course(String courseCode, String description, String collegeCode, String status) {
        this.courseCode = courseCode;
        this.description = description;
        this.collegeCode = collegeCode;
        this.status = status;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCollegeCode() { return collegeCode; }
    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}