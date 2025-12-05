package com.group2.studentportal.backend.models;

public class Schedule {
    private String scheduleId;
    private String schoolYear;
    private String semester;
    private String subjectCode;
    private String section;
    private String day;
    private String room;
    private String timeSlot;
    private String employeeNo;
    private String facultyName;
    private String status;

    public Schedule() {}

    // Getters and Setters
    public String getScheduleId() { return scheduleId; }
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }

    public String getSchoolYear() { return schoolYear; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }

    public String getSemester() { return semester; } // NEW
    public void setSemester(String semester) { this.semester = semester; } // NEW

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getEmployeeNo() { return employeeNo; }
    public void setEmployeeNo(String employeeNo) { this.employeeNo = employeeNo; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}