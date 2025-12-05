package com.group2.studentportal.backend.models;

public class College {
    private String collegeCode;
    private String description;
    private String status;

    public College() {}

    public College(String collegeCode, String description, String status) {
        this.collegeCode = collegeCode;
        this.description = description;
        this.status = status;
    }

    public String getCollegeCode() { return collegeCode; }
    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}