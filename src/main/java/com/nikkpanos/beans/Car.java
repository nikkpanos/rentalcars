package com.nikkpanos.beans;


public class Car {
    private int carid;
    private String licensePlate;
    private String model;
    private int typeid;
    private int sizeid;
    private int seats;
    private int doors;
    private String conventional;
    private float pricePerDay;
    private int storeid;

    public Car(){}// Default constructor

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

    public int getTypeid() {
        return typeid;
    }// getTypeid

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }// setTypeid

    public int getSizeid() {
        return sizeid;
    }// getSizeid

    public void setSizeid(int sizeid) {
        this.sizeid = sizeid;
    }// setSizeid

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

}// class
