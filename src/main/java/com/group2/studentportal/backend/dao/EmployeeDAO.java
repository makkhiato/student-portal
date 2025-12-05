package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // READ (View, Search, Sort)
    public List<Employee> getEmployees(String searchQuery, String sortOrder) {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM employee_master_view WHERE status != 'Inactive' ");

            // SEARCH
            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                sql.append("AND (LOWER(lastname) LIKE ? OR LOWER(firstname) LIKE ?) ");
            }

            // SORT (By Employee Number)
            if (sortOrder != null && (sortOrder.contains("Descend") || sortOrder.contains("Z to A"))) {
                sql.append("ORDER BY employee_no DESC");
            } else {
                sql.append("ORDER BY employee_no ASC");
            }

            stmt = conn.prepareStatement(sql.toString());

            if (searchQuery != null && !searchQuery.trim().isEmpty() && !searchQuery.equals("Search")) {
                String query = "%" + searchQuery.toLowerCase() + "%";
                stmt.setString(1, query);
                stmt.setString(2, query);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeNo(rs.getString("employee_no"));
                e.setFirstName(rs.getString("firstname"));
                e.setLastName(rs.getString("lastname"));
                e.setEmail(rs.getString("email"));
                e.setContactNo(rs.getString("contact_no"));
                e.setAddress(rs.getString("address"));
                e.setGender(rs.getString("gender"));
                e.setBirthday(rs.getDate("birthday"));
                e.setStatus(rs.getString("status"));
                list.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // GET SINGLE (For Edit)
    public Employee getEmployeeByNo(String empNo) {
        Employee e = null;
        String sql = "SELECT * FROM account a JOIN employee emp ON a.account_no = emp.account_no WHERE emp.employee_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                e = new Employee();
                e.setEmployeeNo(rs.getString("employee_no"));
                e.setFirstName(rs.getString("firstname"));
                e.setLastName(rs.getString("lastname"));
                e.setEmail(rs.getString("email"));
                e.setPassword(rs.getString("password"));
                e.setContactNo(rs.getString("contact_no"));
                e.setAddress(rs.getString("address"));
                e.setGender(rs.getString("gender"));
                e.setBirthday(rs.getDate("birthday"));
                e.setStatus(rs.getString("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    // ADD
    public boolean addEmployee(Employee e) {
        Connection conn = null;
        PreparedStatement stmtAccount = null;
        PreparedStatement stmtEmployee = null;
        PreparedStatement stmtRole = null;
        boolean isSuccess = false;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start Transaction

            // 1. Insert into ACCOUNT
            // Note: We use the Employee Number as the Account Number
            String sqlAccount = "INSERT INTO account (account_no, firstname, lastname, password, email, contact_no, address, gender, birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtAccount = conn.prepareStatement(sqlAccount);
            stmtAccount.setString(1, e.getEmployeeNo());
            stmtAccount.setString(2, e.getFirstName());
            stmtAccount.setString(3, e.getLastName());
            stmtAccount.setString(4, e.getPassword());
            stmtAccount.setString(5, e.getEmail());
            stmtAccount.setString(6, e.getContactNo());
            stmtAccount.setString(7, e.getAddress());
            stmtAccount.setString(8, e.getGender());
            stmtAccount.setDate(9, e.getBirthday());
            stmtAccount.executeUpdate();

            // 2. Insert into EMPLOYEE
            String sqlEmployee = "INSERT INTO employee (employee_no, account_no, status) VALUES (?, ?, ?)";
            stmtEmployee = conn.prepareStatement(sqlEmployee);
            stmtEmployee.setString(1, e.getEmployeeNo());
            stmtEmployee.setString(2, e.getEmployeeNo()); // Link to Account
            stmtEmployee.setString(3, e.getStatus());
            stmtEmployee.executeUpdate();

            // 3. Insert into ACCOUNT_ROLES (Role 2 = Employee/Faculty)
            // Make sure role_id 2 exists in your ROLES table!
            String sqlRole = "INSERT INTO account_roles (account_no, role_id) VALUES (?, 2)";
            stmtRole = conn.prepareStatement(sqlRole);
            stmtRole.setString(1, e.getEmployeeNo());
            stmtRole.executeUpdate();

            conn.commit(); // Commit Transaction
            isSuccess = true;
            System.out.println("Employee added successfully: " + e.getEmployeeNo());

        } catch (SQLException ex) {
            // ROLLBACK on error
            try { if (conn != null) conn.rollback(); } catch (SQLException x) { x.printStackTrace(); }

            // PRINT THE REAL ERROR
            System.err.println("ADD EMPLOYEE FAILED: " + ex.getMessage());
            ex.printStackTrace();
            isSuccess = false;
        } finally {
            // Close everything
            try { if (stmtAccount != null) stmtAccount.close(); } catch (Exception x) {}
            try { if (stmtEmployee != null) stmtEmployee.close(); } catch (Exception x) {}
            try { if (stmtRole != null) stmtRole.close(); } catch (Exception x) {}
            try { if (conn != null) conn.close(); } catch (Exception x) {}
        }
        return isSuccess;
    }

    // UPDATE
    public boolean updateEmployee(Employee e) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Update Account
            String sqlAcc = "UPDATE account SET firstname=?, lastname=?, email=?, contact_no=?, address=?, gender=?, birthday=?, password=? WHERE account_no=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAcc)) {
                ps.setString(1, e.getFirstName());
                ps.setString(2, e.getLastName());
                ps.setString(3, e.getEmail());
                ps.setString(4, e.getContactNo());
                ps.setString(5, e.getAddress());
                ps.setString(6, e.getGender());
                ps.setDate(7, e.getBirthday());
                ps.setString(8, e.getPassword());
                ps.setString(9, e.getEmployeeNo());
                ps.executeUpdate();
            }

            // Update Employee Status
            String sqlEmp = "UPDATE employee SET status=? WHERE employee_no=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlEmp)) {
                ps.setString(1, e.getStatus());
                ps.setString(2, e.getEmployeeNo());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception x) {}
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception x) {}
        }
    }

    // SOFT DELETE
    public boolean softDeleteEmployee(String empNo) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE employee SET status = 'Inactive' WHERE employee_no = ?")) {
            stmt.setString(1, empNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}