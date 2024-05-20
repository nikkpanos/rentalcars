package com.nikkpanos.beans;

// This class can be used for the available_car_info and not_available_car_info views
// and the result set of the two stored procedures too
// because they all have the exact same attributes (columns in mysql)


public class FullCarInfoView {
    private int carid;
    private String licensePlate;
    private String model;
    private String type;
    private String size;
    private int seats;
    private int doors;
    private String conventional;
    private float pricePerDay;
    private int storeid;
    private String city;

    public FullCarInfoView(){}// Default constructor

    public int getCarid() {
        return carid;
    }// getCarid

    public void setCarid(int carid) {
        this.carid = carid;
    }// setCarid

    public String getLicensePlate() {
        return licensePlate;
    }// getLicensePlate

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }// setLicensePlate

    public String getModel() {
        return model;
    }// getModel

    public void setModel(String model) {
        this.model = model;
    }// setModel

    public String getType() {
        return type;
    }// getType

    public void setType(String type) {
        this.type = type;
    }// setType

    public String getSize() {
        return size;
    }// getSize

    public void setSize(String size) {
        this.size = size;
    }// setSize

    public int getSeats() {
        return seats;
    }// getSeats

    public void setSeats(int seats) {
        this.seats = seats;
    }// setSeats

    public int getDoors() {
        return doors;
    }// getDoors

    public void setDoors(int doors) {
        this.doors = doors;
    }// setDoors

    public String getConventional() {
        return conventional;
    }// getConventional

    public void setConventional(String conventional) {
        this.conventional = conventional;
    }// setConventional

    public float getPricePerDay() {
        return pricePerDay;
    }// getPricePerDay

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }// setPricePerDay

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

}// class
