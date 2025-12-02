package com.group2.studentportal.backend.dao;

import com.group2.studentportal.backend.DBConnection;
import com.group2.studentportal.backend.models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    //Login Logic
    public Account login(String accountNo, String password, String selectedRole) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Account loggedInAccount = null;

        try {
            conn = DBConnection.getConnection();

            // SQL Logic
            String sql = "SELECT a.*, r.role_name " +
                    "FROM account a " +
                    "JOIN account_roles ar ON a.account_no = ar.account_no " +
                    "JOIN roles r ON ar.role_id = r.role_id " +
                    "WHERE a.account_no = ? " +
                    "AND a.password = ? " +
                    "AND r.role_name = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNo);
            stmt.setString(2, password);
            stmt.setString(3, selectedRole);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Login Success
                loggedInAccount = new Account();
                loggedInAccount.setAccountNo(rs.getString("account_no"));
                loggedInAccount.setFirstName(rs.getString("firstname"));
                loggedInAccount.setLastName(rs.getString("lastname"));
                loggedInAccount.setMiddleName(rs.getString("middlename"));
                loggedInAccount.setPassword(rs.getString("password"));
                loggedInAccount.setEmail(rs.getString("email"));
                loggedInAccount.setContactNo(rs.getString("contact_no"));
                loggedInAccount.setAddress(rs.getString("address"));
                loggedInAccount.setGender(rs.getString("gender"));
                loggedInAccount.setBirthday(rs.getDate("birthday"));

                // Store the role
                loggedInAccount.setRole(rs.getString("role_name"));
            }

        } catch (SQLException e) {
            System.err.println("Login Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return loggedInAccount;
    }

    //Update Logic
    public boolean updateAccount(Account account) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isSuccess = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "UPDATE account SET " +
                    "firstname = ?, " +
                    "lastname = ?, " +
                    "middlename = ?, " +
                    "gender = ?, " +
                    "birthday = ?, " +
                    "email = ?, " +
                    "address = ? " +
                    "WHERE account_no = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, account.getFirstName());
            stmt.setString(2, account.getLastName());
            stmt.setString(3, account.getMiddleName());
            stmt.setString(4, account.getGender());
            stmt.setDate(5, account.getBirthday());
            stmt.setString(6, account.getEmail());
            stmt.setString(7, account.getAddress());

            stmt.setString(8, account.getAccountNo());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                isSuccess = true;
                System.out.println("Account updated successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
}