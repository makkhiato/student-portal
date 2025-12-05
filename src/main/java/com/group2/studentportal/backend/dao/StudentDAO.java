package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // --- 1. READ (View + Search + Sort) ---
    public List<Student> getStudents(String searchQuery, String sortOrder) {
        List<Student> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM student_master_view WHERE status != 'Inactive' ");

            // SEARCH: By First or Last Name
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND (LOWER(lastname) LIKE ? OR LOWER(firstname) LIKE ?) ");
            }

            // SORT: By Student Number
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY student_no DESC");
            } else {
                sql.append("ORDER BY student_no ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                String query = "%" + searchQuery.toLowerCase() + "%";
                stmt.setString(1, query);
                stmt.setString(2, query);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setStudentNo(rs.getString("student_no"));
                s.setFirstName(rs.getString("firstname"));
                s.setLastName(rs.getString("lastname"));
                s.setEmail(rs.getString("email"));
                s.setContactNo(rs.getString("contact_no"));
                s.setAddress(rs.getString("address"));
                s.setGender(rs.getString("gender"));
                s.setBirthday(rs.getDate("birthday"));
                s.setCourseCode(rs.getString("course"));
                s.setYearLevel(rs.getString("year_level"));
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

    // --- 2. GET SINGLE (For Editing) ---
    public Student getStudentByNo(String studentNo) {
        Student s = null;
        // Querying the underlying tables to get everything (including password if needed)
        String sql = "SELECT * FROM account a JOIN student s ON a.account_no = s.account_no WHERE s.student_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                s = new Student();
                s.setStudentNo(rs.getString("student_no"));
                s.setFirstName(rs.getString("firstname"));
                s.setLastName(rs.getString("lastname"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setContactNo(rs.getString("contact_no"));
                s.setAddress(rs.getString("address"));
                s.setGender(rs.getString("gender"));
                s.setBirthday(rs.getDate("birthday"));
                s.setCourseCode(rs.getString("first_choice"));
                s.setStatus(rs.getString("status"));
                s.setYearLevel(rs.getString("year_level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    // --- 3. ADD ---
    public boolean addStudent(Student s) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlAcc = "INSERT INTO account (account_no, firstname, lastname, password, email, contact_no, address, gender, birthday) VALUES (?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlAcc)) {
                ps.setString(1, s.getStudentNo());
                ps.setString(2, s.getFirstName());
                ps.setString(3, s.getLastName());
                ps.setString(4, s.getPassword());
                ps.setString(5, s.getEmail());
                ps.setString(6, s.getContactNo());
                ps.setString(7, s.getAddress());
                ps.setString(8, s.getGender());
                ps.setDate(9, s.getBirthday());
                ps.executeUpdate();
            }

            String sqlStud = "INSERT INTO student (student_no, account_no, first_choice, status, year_level) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlStud)) {
                ps.setString(1, s.getStudentNo());
                ps.setString(2, s.getStudentNo());
                ps.setString(3, s.getCourseCode());
                ps.setString(4, s.getStatus());
                ps.setString(5, s.getYearLevel());
                ps.executeUpdate();
            }

            String sqlRole = "INSERT INTO account_roles (account_no, role_id) VALUES (?, 1)";
            try (PreparedStatement ps = conn.prepareStatement(sqlRole)) {
                ps.setString(1, s.getStudentNo());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // --- 4. UPDATE ---
    public boolean updateStudent(Student s) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlAcc = "UPDATE account SET firstname=?, lastname=?, email=?, contact_no=?, address=?, gender=?, birthday=?, password=? WHERE account_no=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAcc)) {
                ps.setString(1, s.getFirstName());
                ps.setString(2, s.getLastName());
                ps.setString(3, s.getEmail());
                ps.setString(4, s.getContactNo());
                ps.setString(5, s.getAddress());
                ps.setString(6, s.getGender());
                ps.setDate(7, s.getBirthday());
                ps.setString(8, s.getPassword());
                ps.setString(9, s.getStudentNo());
                ps.executeUpdate();
            }

            String sqlStud = "UPDATE student SET first_choice=?, status=?, year_level=? WHERE student_no=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlStud)) {
                ps.setString(1, s.getCourseCode());
                ps.setString(2, s.getStatus());
                ps.setString(3, s.getYearLevel());
                ps.setString(4, s.getStudentNo());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // --- 5. SOFT DELETE ---
    public boolean softDeleteStudent(String studentNo) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE student SET status = 'Inactive' WHERE student_no = ?")) {
            stmt.setString(1, studentNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}