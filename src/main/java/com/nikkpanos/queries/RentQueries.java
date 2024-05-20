package com.nikkpanos.queries;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.nikkpanos.beans.Rent;
import com.nikkpanos.utilities.ConnectionUtils;
import com.nikkpanos.beans.FullRentInfoView;

// Not extendable class. Will have only static methods
public final class RentQueries {
    private static Connection con = null;

    private RentQueries(){}// This class is not meant to generate objects

    // Χρήση αμέσως μετά την επιλογή όλων των στοιχείων της νέας προς καταχώριση ενοικίασης
    public static int insertNewRent(Rent rent) throws ClassNotFoundException, SQLException{
        String customerDriverLicense = rent.getCustomerDriverLicense();
        int carid = rent.getCarid();
        int deliveryLocationid = rent.getDeliveryLocationid();
        Timestamp deliveryDate = rent.getDeliveryDate();
        int returnLocationid = rent.getReturnLocationid();
        Timestamp returnDate = rent.getReturnDate();

        con = ConnectionUtils.getConnection();
        String query = "INSERT INTO rent (customerdriverlicense, carid, deliverylocationid, deliverydate, returnlocationid, "+
                "returndate) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setString(1, customerDriverLicense);
        stmnt.setInt(2, carid);
        stmnt.setInt(3, deliveryLocationid);
        stmnt.setTimestamp(4, deliveryDate);
        stmnt.setInt(5, returnLocationid);
        stmnt.setTimestamp(6, returnDate);
        int count = stmnt.executeUpdate();

        stmnt.close();
        con.close();
        return count;
    }// insertNewRent

    // Χρήση αμέσως μετά την επιλογή όλων των νέων στοιχείων της προς τροποποίηση ενοικίασης
    public static int updateRent(Rent rent) throws ClassNotFoundException, SQLException{
        int rentid = rent.getRentid();
        String customerDriverLicense = rent.getCustomerDriverLicense();
        int carid = rent.getCarid();
        int deliveryLocationid = rent.getDeliveryLocationid();
        Timestamp deliveryDate = rent.getDeliveryDate();
        String delivered = rent.getDelivered();
        int returnLocationid = rent.getReturnLocationid();
        Timestamp returnDate = rent.getReturnDate();
        String returned = rent.getReturned();

        con = ConnectionUtils.getConnection();
        String query = "UPDATE rent SET customerdriverlicense=?, carid=?, deliverylocationid=?, deliverydate=?, "+
                "delivered=?, returnlocationid=?, returndate=?, returned=? WHERE rentid=?";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setString(1, customerDriverLicense);
        stmnt.setInt(2, carid);
        stmnt.setInt(3, deliveryLocationid);
        stmnt.setTimestamp(4, deliveryDate);
        stmnt.setString(5, delivered);
        stmnt.setInt(6, returnLocationid);
        stmnt.setTimestamp(7, returnDate);
        stmnt.setString(8, returned);
        stmnt.setInt(9, rentid);
        int count = stmnt.executeUpdate();

        stmnt.close();
        con.close();
        return count;
    }// updateRent

    // Χρήση αμέσως μετά την επιλογή τροποποίησης στοιχείου/ων υπάρχουσας ενοικίασης
    public static List<FullRentInfoView> getFullRentInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        String query = "SELECT * FROM full_rent_info WHERE storeid=" + storeid + ";";
        Statement stmnt = con.createStatement();
        ResultSet rs = stmnt.executeQuery(query);
        List<FullRentInfoView> rents = new ArrayList<FullRentInfoView>();
        while(rs.next()){
            FullRentInfoView rent = new FullRentInfoView();
            rent.setRentid(rs.getInt("rentid"));
            rent.setCustomerDriverLicense(rs.getString("customerdriverlicense"));
            rent.setFirstName(rs.getString("firstname"));
            rent.setLastName(rs.getString("lastname"));
            rent.setCellphoneNumber(rs.getString("cellphoneNumber"));
            rent.setCarid(rs.getInt("carid"));
            rent.setModel(rs.getString("model"));
            rent.setCity(rs.getString("city"));
            rent.setStoreid(rs.getInt("storeid"));
            rent.setDeliveryLocationid(rs.getInt("deliverylocationid"));
            rent.setDeliveryDate(rs.getTimestamp("deliverydate"));
            rent.setDelivered(rs.getString("delivered"));
            rent.setReturnLocationid(rs.getInt("returnlocationid"));
            rent.setReturnDate(rs.getTimestamp("returndate"));
            rent.setReturned(rs.getString("returned"));
            rent.setPricePerDay(rs.getFloat("priceperday"));
            rent.setDays(rs.getFloat("days"));
            rent.setTotalPrice(rs.getFloat("totalprice"));
            rents.add(rent);
        }// while

        stmnt.close();
        con.close();
        return rents;
    }// getFullRentInfoByStoreid

    // Χρήση αμέσως μετά την επιλογή του rentid της προς τροποποίηση ενοικίασης
    public static Rent getRentByRentid(int rentid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM rent WHERE rentid=" + rentid + ";";
        ResultSet rs = stmnt.executeQuery(query);
        if (rs.next()){
            Rent rent = new Rent();
            rent.setRentid(rs.getInt("rentid"));
            rent.setCustomerDriverLicense(rs.getString("customerdriverlicense"));
            rent.setCarid(rs.getInt("carid"));
            rent.setDeliveryLocationid(rs.getInt("deliverylocationid"));
            rent.setDeliveryDate(rs.getTimestamp("deliverydate"));
            rent.setDelivered(rs.getString("delivered"));
            rent.setReturnLocationid(rs.getInt("returnlocationid"));
            rent.setReturnDate(rs.getTimestamp("returndate"));
            rent.setReturned(rs.getString("returned"));

            stmnt.close();
            con.close();
            return rent;
        }// if

        stmnt.close();
        con.close();
        return null;
    }// getRentByRentid

    // Χρήση αμέσως μετά την επιλογή storeid (στην έναρξη της εφαρμογής) για υπενθύμιση των εκκρεμών ενοικιάσεων του καταστήματος
    public static List<FullRentInfoView> getPendingRentInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM pending_rent_info WHERE storeid=" + storeid + ";";
        ResultSet rs = stmnt.executeQuery(query);
        List<FullRentInfoView> rents = new ArrayList<FullRentInfoView>();
        while(rs.next()){
            FullRentInfoView rent = new FullRentInfoView();
            rent.setRentid(rs.getInt("rentid"));
            rent.setCustomerDriverLicense(rs.getString("customerdriverlicense"));
            rent.setFirstName(rs.getString("firstname"));
            rent.setLastName(rs.getString("lastname"));
            rent.setCellphoneNumber(rs.getString("cellphoneNumber"));
            rent.setCarid(rs.getInt("carid"));
            rent.setModel(rs.getString("model"));
            rent.setCity(rs.getString("city"));
            rent.setStoreid(rs.getInt("storeid"));
            rent.setDeliveryLocationid(rs.getInt("deliverylocationid"));
            rent.setDeliveryDate(rs.getTimestamp("deliverydate"));
            rent.setDelivered(rs.getString("delivered"));
            rent.setReturnLocationid(rs.getInt("returnlocationid"));
            rent.setReturnDate(rs.getTimestamp("returndate"));
            rent.setReturned(rs.getString("returned"));
            rent.setPricePerDay(rs.getFloat("priceperday"));
            rent.setDays(rs.getFloat("days"));
            rent.setTotalPrice(rs.getFloat("totalprice"));
            rents.add(rent);
        }// while

        stmnt.close();
        con.close();
        return rents;
    }// getPendingRentInfoByStoreid

}// class

