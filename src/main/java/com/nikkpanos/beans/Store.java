package com.nikkpanos.beans;

public class Store {
    private int storeid;
    private String city;
    private String addressName;
    private String addressNumber;
    private int postalCode;

    public Store(){}// Default constructor

    public int getStoreid() {
        return storeid;
    }// getStoreid

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }// setStoreid

    public String getCity() {
        return city;
    }// getCity

    public void setCity(String city) {
        this.city = city;
    }// setCity

    public String getAddressName() {
        return addressName;
    }// getAddressName

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }// setAddressName

    public String getAddressNumber() {
        return addressNumber;
    }// getAddressNumber

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }// setAddressNumber

    public int getPostalCode() {
        return postalCode;
    }// getPostaCode

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }// setPostalCode

}// class
