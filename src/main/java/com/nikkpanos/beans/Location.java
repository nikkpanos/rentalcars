package com.nikkpanos.beans;

public class Location {
    private int locationid;
    private String addressName;
    private String addressNumber;
    private int postalCode;
    private int storeid;

    public Location(){}// Default constructor

    public int getLocationid() {
        return locationid;
    }// getLocationid

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }// setLocationid

    public String getAddressName() {
        return addressName;
    }// getAddressName

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }// set AddressName

    public String getAddressNumber() {
        return addressNumber;
    }// getAddressNumber

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }// setAddressNumber

    public int getPostalCode() {
        return postalCode;
    }// getPostalCode

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }// setPostaCode

    public int getStoreid() {
        return storeid;
    }// getStoreid

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }// setStoreid

}// class
