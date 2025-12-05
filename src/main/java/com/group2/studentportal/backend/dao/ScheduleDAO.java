package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Schedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    // READ (With Filters for Year & Sem)
    public List<Schedule> getSchedules(String searchQuery, String sortOrder, String schoolYearFilter, String semesterFilter) {
        List<Schedule> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM schedule_master_view WHERE status != 'Inactive' ");

            // FILTERS
            if (schoolYearFilter != null && !schoolYearFilter.equals("School Year")) {
                sql.append("AND school_year = ? ");
            }
            if (semesterFilter != null && !semesterFilter.equals("Semester")) {
                sql.append("AND semester = ? ");
            }
            // SEARCH
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND LOWER(subject_code) LIKE ? ");
            }

            // SORT
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY subject_code DESC");
            } else {
                sql.append("ORDER BY subject_code ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (schoolYearFilter != null && !schoolYearFilter.equals("School Year")) {
                stmt.setString(paramIndex++, schoolYearFilter);
            }
            if (semesterFilter != null && !semesterFilter.equals("Semester")) {
                stmt.setString(paramIndex++, semesterFilter);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                stmt.setString(paramIndex++, "%" + searchQuery.toLowerCase() + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Schedule s = new Schedule();
                s.setScheduleId(rs.getString("schedule_id"));
                s.setSchoolYear(rs.getString("school_year"));
                s.setSemester(rs.getString("semester"));
                s.setSubjectCode(rs.getString("subject_code"));
                s.setSection(rs.getString("section"));
                s.setDay(rs.getString("day"));
                s.setRoom(rs.getString("room"));
                s.setTimeSlot(rs.getString("time_slot"));
                s.setEmployeeNo(rs.getString("employee_no"));
                s.setFacultyName(rs.getString("faculty_name"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // GET SINGLE
    public Schedule getScheduleById(String id) {
        Schedule s = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM schedule_master_view WHERE schedule_id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                s = new Schedule();
                s.setScheduleId(rs.getString("schedule_id"));
                s.setSchoolYear(rs.getString("school_year"));
                s.setSemester(rs.getString("semester"));
                s.setSubjectCode(rs.getString("subject_code"));
                s.setSection(rs.getString("section"));
                s.setDay(rs.getString("day"));
                s.setRoom(rs.getString("room"));
                s.setTimeSlot(rs.getString("time_slot"));
                s.setEmployeeNo(rs.getString("employee_no"));
                s.setFacultyName(rs.getString("faculty_name"));
                s.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return s;
    }

    public boolean subjectExists(String code) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM subject WHERE subject_code = ?")) {
            stmt.setString(1, code);
            return stmt.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    // ADD (Updated with Semester)
    public boolean addSchedule(Schedule s) {
        if (!subjectExists(s.getSubjectCode())) return false;

        String sql = "INSERT INTO schedule (schedule_id, school_year, semester, subject_code, section, day, room, time_slot, employee_no, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getScheduleId());
            stmt.setString(2, s.getSchoolYear());
            stmt.setString(3, s.getSemester()); // Added
            stmt.setString(4, s.getSubjectCode());
            stmt.setString(5, s.getSection());
            stmt.setString(6, s.getDay());
            stmt.setString(7, s.getRoom());
            stmt.setString(8, s.getTimeSlot());
            stmt.setString(9, s.getEmployeeNo());
            stmt.setString(10, s.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // UPDATE (Updated with Semester)
    public boolean updateSchedule(Schedule s) {
        if (!subjectExists(s.getSubjectCode())) return false;

        String sql = "UPDATE schedule SET school_year=?, semester=?, subject_code=?, section=?, day=?, room=?, time_slot=?, employee_no=?, status=? WHERE schedule_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getSchoolYear());
            stmt.setString(2, s.getSemester()); // Added
            stmt.setString(3, s.getSubjectCode());
            stmt.setString(4, s.getSection());
            stmt.setString(5, s.getDay());
            stmt.setString(6, s.getRoom());
            stmt.setString(7, s.getTimeSlot());
            stmt.setString(8, s.getEmployeeNo());
            stmt.setString(9, s.getStatus());
            stmt.setString(10, s.getScheduleId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // SOFT DELETE
    public boolean softDeleteSchedule(String id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE schedule SET status = 'Inactive' WHERE schedule_id = ?")) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}