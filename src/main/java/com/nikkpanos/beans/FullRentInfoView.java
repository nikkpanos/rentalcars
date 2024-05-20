package com.nikkpanos.beans;


import java.sql.Timestamp;

public class FullRentInfoView {
    private int rentid;
    private String customerDriverLicense;
    private String firstName;
    private String lastName;
    private String cellphoneNumber;
    private int carid;
    private String model;
    private String city;
    private int storeid;
    private int deliveryLocationid;
    private Timestamp deliveryDate;
    private String delivered;
    private int returnLocationid;
    private Timestamp returnDate;
    private String returned;
    private float pricePerDay;
    private float days;
    private float totalPrice;

    public FullRentInfoView(){}// Default constructor

    public int getRentid() {
        return rentid;
    }// getRentid

    public void setRentid(int rentid) {
        this.rentid = rentid;
    }// setRentid

    public String getCustomerDriverLicense() {
        return customerDriverLicense;
    }// getCustomerDriverLicense

    public void setCustomerDriverLicense(String customerDriverLicense) {
        this.customerDriverLicense = customerDriverLicense;
    }// setCustomerDriverLicense

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

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }// getCellphoneNumber

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }// setCellphoneNumber

    public int getCarid() {
        return carid;
    }// getCarid

    public void setCarid(int carid) {
        this.carid = carid;
    }// setCarid

    public String getModel(){
        return model;
    }// getModel

    public void setModel(String model){
        this.model = model;
    }// setModel

    public String getCity() {
        return city;
    }// getCity

    public void setCity(String city) {
        this.city = city;
    }// setCity

    public int getStoreid() {
        return storeid;
    }// getStoreid

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }// setStoreid

    public int getDeliveryLocationid() {
        return deliveryLocationid;
    }// getDeliveryLocationid

    public void setDeliveryLocationid(int deliveryLocationid) {
        this.deliveryLocationid = deliveryLocationid;
    }// setDeliveryLocationid

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }// getDeliveyDate

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }// setDeliveryDate

    public String getDelivered() {
        return delivered;
    }// getDelivered

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }// setDelivered

    public int getReturnLocationid() {
        return returnLocationid;
    }// getLocationid

    public void setReturnLocationid(int returnLocationid) {
        this.returnLocationid = returnLocationid;
    }// setLocationid

    public Timestamp getReturnDate() {
        return returnDate;
    }// getReturnedDate

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }// setReturnedDate

    public String getReturned() {
        return returned;
    }// getReturned

    public void setReturned(String returned) {
        this.returned = returned;
    }// setReturned

    public float getPricePerDay() {
        return pricePerDay;
    }// getPricePerDay

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }// setPricePerDay

    public float getDays() {
        return days;
    }// getDays

    public void setDays(float days) {
        this.days = days;
    }// setDays

    public float getTotalPrice() {
        return totalPrice;
    }// getTotalPrice

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }// setTotalPrice
}// class
