package com.group2.studentportal.backend.models;

import java.sql.Date;

public class Account {

    // Stored Data
    private String accountNo;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private String contactNo;
    private String address;
    private String gender;
    private Date birthday; // usage: java.sql.Date

    // For Login logic
    private String role;

    // Constructor (Empty)
    public Account() {
    }

    // Constructor (Full)
    public Account(String accountNo, String firstName, String lastName, String middleName,
                   String password, String email, String contactNo, String address,
                   String gender, Date birthday, String role) {
        this.accountNo = accountNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.address = address;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
    }

    // Getters and Setters

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}