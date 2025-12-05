package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // READ (View, Search, Sort)
    public List<Course> getCourses(String searchQuery, String sortOrder) {
        List<Course> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM course_master_view WHERE status != 'Inactive' ");

            // SEARCH (By Description)
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND LOWER(description) LIKE ? ");
            }

            // SORT (By Course Code)
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY course_code DESC");
            } else {
                sql.append("ORDER BY course_code ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                stmt.setString(1, "%" + searchQuery.toLowerCase() + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Course c = new Course();
                c.setCourseCode(rs.getString("course_code"));
                c.setDescription(rs.getString("description"));
                c.setCollegeCode(rs.getString("college_code"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // GET SINGLE (For Edit)
    public Course getCourseByCode(String code) {
        Course c = null;
        String sql = "SELECT * FROM course WHERE course_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Course();
                c.setCourseCode(rs.getString("course_code"));
                c.setDescription(rs.getString("description"));
                c.setCollegeCode(rs.getString("college_code"));
                c.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // VALIDATE COLLEGE EXISTS (Business Rule)
    public boolean collegeExists(String collegeCode) {
        boolean exists = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM college WHERE college_code = ? AND status != 'Inactive'")) {
            stmt.setString(1, collegeCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) exists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // ADD
    public boolean addCourse(Course c) {
        if (!collegeExists(c.getCollegeCode())) {
            System.err.println("Error: College Code " + c.getCollegeCode() + " does not exist.");
            return false;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO course (course_code, description, college_code, status) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, c.getCourseCode());
            stmt.setString(2, c.getDescription());
            stmt.setString(3, c.getCollegeCode());
            stmt.setString(4, c.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateCourse(Course c) {
        if (!collegeExists(c.getCollegeCode())) return false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE course SET description=?, college_code=?, status=? WHERE course_code=?")) {

            stmt.setString(1, c.getDescription());
            stmt.setString(2, c.getCollegeCode());
            stmt.setString(3, c.getStatus());
            stmt.setString(4, c.getCourseCode());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SOFT DELETE
    public boolean softDeleteCourse(String code) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE course SET status = 'Inactive' WHERE course_code = ?")) {
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}