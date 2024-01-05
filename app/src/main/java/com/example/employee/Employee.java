package com.example.employee;

//Class Employee
public class Employee {
    //Characteristics of each Employee
    public String employeeId;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String position;

    //Constructor used to initialise instances of the Employee class
    public Employee(String id, String firstName, String lastName, String phoneNumber, String email, String position){
        this.employeeId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
    }
}
