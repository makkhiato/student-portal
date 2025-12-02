package com.group2.studentportal;

import com.group2.studentportal.backend.dao.AccountDAO;
import com.group2.studentportal.backend.models.Account;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- STARTING BACKEND TEST ---");

        // 1. Create the DAO (The Tool)
        AccountDAO dao = new AccountDAO();

        // 2. Define our test credentials (matches the SQL above)
        String testID = "2023-001";
        String testPass = "password123";
        String testRole = "Student";

        System.out.println("Attempting to login with ID: " + testID);

        // 3. Run the Login Logic
        Account user = dao.login(testID, testPass, testRole);

        // 4. Check the result
        if (user != null) {
            System.out.println("SUCCESS! Login Verified.");
            System.out.println("User Name: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("User Role: " + user.getRole());
            System.out.println("Email: " + user.getEmail());
        } else {
            System.err.println("FAILURE: Login returned null.");
            System.err.println("Check if:");
            System.err.println(" - The Database IP in DBConnection.java is correct.");
            System.err.println(" - The SQL Sample Data was actually inserted and COMMITted.");
        }

        System.out.println("--- TEST COMPLETE ---");
    }
}