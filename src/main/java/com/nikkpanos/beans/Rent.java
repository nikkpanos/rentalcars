package com.nikkpanos.beans;

import java.sql.Timestamp;

public class Rent {
    private int rentid;
    private String customerDriverLicense;
    private int carid;
    private int deliveryLocationid;
    private Timestamp deliveryDate;
    private String delivered;
    private int returnLocationid;
    private Timestamp returnDate;
    private String returned;

    public Rent(){}// Default constructor

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

    public int getCarid() {
        return carid;
    }// getCarid

    public void setCarid(int carid) {
        this.carid = carid;
    }// setCarid

    public int getDeliveryLocationid() {
        return deliveryLocationid;
    }// getDeliveryLocationid

    public void setDeliveryLocationid(int deliveryLocationid) {
        this.deliveryLocationid = deliveryLocationid;
    }// setDeliveryLocationid

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }// getDeliveryDate

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
    }// getReturnDate

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }// setReturnDate

    public String getReturned() {
        return returned;
    }// getReturned

    public void setReturned(String returned) {
        this.returned = returned;
    }// setReturned
}// class
