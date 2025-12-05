package com.group2.studentportal.backend.models;

public class Subject {
    private String subjectCode;
    private String description;
    private double units;
    private String collegeCode;
    private String status;

    public Subject() {}

    public Subject(String subjectCode, String description, double units, String collegeCode, String status) {
        this.subjectCode = subjectCode;
        this.description = description;
        this.units = units;
        this.collegeCode = collegeCode;
        this.status = status;
    }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getUnits() { return units; }
    public void setUnits(double units) { this.units = units; }

    public String getCollegeCode() { return collegeCode; }
    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}