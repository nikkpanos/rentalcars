package com.nikkpanos.beans;

public class Customer {
    private String driverLicense;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphoneNumber;
    private String homephoneNumber;

    public Customer(){}// Default constructor

    public String getDriverLicense() {
        return driverLicense;
    }// getDriverLicense

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }// setDriverLicense

    public String getFirstName() {
        return firstName;
    }// getFirstName

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }// setFirstName

    public String getLastName() {
        return lastName;
    }// getLastName

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }// setLastName

    public String getEmail() {
        return email;
    }// getEmail

    public void setEmail(String email) {
        this.email = email;
    }// setEmail

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }// getCellphoneNumber

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }// setCellphoneNumber

    public String getHomephoneNumber() {
        return homephoneNumber;
    }// getHomephoneNumber

    public void setHomephoneNumber(String homephoneNumber) {
        this.homephoneNumber = homephoneNumber;
    }// setHomephoneNumber
}// class
