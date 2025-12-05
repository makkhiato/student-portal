package com.group2.studentportal.backend.models;

import java.sql.Date;

public class Employee extends Account {

    private String employeeNo;
    private String status;

    public Employee() {
        super();
    }

    public Employee(String accountNo, String firstName, String lastName, String middleName,
                    String password, String email, String contactNo, String address,
                    String gender, Date birthday, String role,
                    String employeeNo, String status) {

        super(accountNo, firstName, lastName, middleName, password, email,
                contactNo, address, gender, birthday, role);

        this.employeeNo = employeeNo;
        this.status = status;
    }

    public String getEmployeeNo() { return employeeNo; }
    public void setEmployeeNo(String employeeNo) { this.employeeNo = employeeNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}