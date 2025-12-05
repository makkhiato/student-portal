package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.College;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {

    // READ (View, Search, Sort)
    public List<College> getColleges(String searchQuery, String sortOrder) {
        List<College> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM college_master_view WHERE status != 'Inactive' ");

            // SEARCH (By Description/Name)
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND LOWER(description) LIKE ? ");
            }

            // SORT (By College Code)
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY college_code DESC");
            } else {
                sql.append("ORDER BY college_code ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                stmt.setString(1, "%" + searchQuery.toLowerCase() + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                College c = new College();
                c.setCollegeCode(rs.getString("college_code"));
                c.setDescription(rs.getString("description"));
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
    public College getCollegeByCode(String code) {
        College c = null;
        String sql = "SELECT * FROM college WHERE college_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new College();
                c.setCollegeCode(rs.getString("college_code"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // ADD
    public boolean addCollege(College c) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO college (college_code, description, status) VALUES (?, ?, ?)")) {

            stmt.setString(1, c.getCollegeCode());
            stmt.setString(2, c.getDescription());
            stmt.setString(3, c.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateCollege(College c) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE college SET description=?, status=? WHERE college_code=?")) {

            stmt.setString(1, c.getDescription());
            stmt.setString(2, c.getStatus());
            stmt.setString(3, c.getCollegeCode());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SOFT DELETE
    public boolean softDeleteCollege(String code) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE college SET status = 'Inactive' WHERE college_code = ?")) {
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}