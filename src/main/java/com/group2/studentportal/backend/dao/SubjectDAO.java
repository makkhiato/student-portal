package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // READ (View, Search, Sort)
    public List<Subject> getSubjects(String searchQuery, String sortOrder) {
        List<Subject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM subject_master_view WHERE status != 'Inactive' ");

            // SEARCH (By Description)
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND LOWER(description) LIKE ? ");
            }

            // SORT (By Subject Code)
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY subject_code DESC");
            } else {
                sql.append("ORDER BY subject_code ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                stmt.setString(1, "%" + searchQuery.toLowerCase() + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectCode(rs.getString("subject_code"));
                s.setDescription(rs.getString("description"));
                s.setUnits(rs.getDouble("units"));
                s.setCollegeCode(rs.getString("college_code"));
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

    // GET SINGLE (For Edit)
    public Subject getSubjectByCode(String code) {
        Subject s = null;
        String sql = "SELECT * FROM subject WHERE subject_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                s = new Subject();
                s.setSubjectCode(rs.getString("subject_code"));
                s.setDescription(rs.getString("description"));
                s.setUnits(rs.getDouble("units"));
                s.setCollegeCode(rs.getString("college_code"));
                s.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    // VALIDATE COLLEGE EXISTS
    public boolean collegeExists(String collegeCode) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM college WHERE college_code = ? AND status != 'Inactive'")) {
            stmt.setString(1, collegeCode);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ADD
    public boolean addSubject(Subject s) {
        if (!collegeExists(s.getCollegeCode())) return false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO subject (subject_code, description, units, college_code, status) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, s.getSubjectCode());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getUnits());
            stmt.setString(4, s.getCollegeCode());
            stmt.setString(5, s.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateSubject(Subject s) {
        if (!collegeExists(s.getCollegeCode())) return false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE subject SET description=?, units=?, college_code=?, status=? WHERE subject_code=?")) {

            stmt.setString(1, s.getDescription());
            stmt.setDouble(2, s.getUnits());
            stmt.setString(3, s.getCollegeCode());
            stmt.setString(4, s.getStatus());
            stmt.setString(5, s.getSubjectCode());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SOFT DELETE
    public boolean softDeleteSubject(String code) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE subject SET status = 'Inactive' WHERE subject_code = ?")) {
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}