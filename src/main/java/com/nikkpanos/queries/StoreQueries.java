package com.nikkpanos.queries;


import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.nikkpanos.beans.Store;
import com.nikkpanos.utilities.ConnectionUtils;
import com.nikkpanos.beans.Location;

public final class StoreQueries {

    private StoreQueries(){}// This class is not meant to generate objects

    private static Connection con = null;

    public static List<Store> getAllStores() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM store";
        ResultSet rs = stmnt.executeQuery(query);
        List<Store> stores = new ArrayList<Store>();
        while(rs.next()){
            Store store = new Store();
            store.setStoreid(rs.getInt("storeid"));
            store.setCity(rs.getString("city"));
            store.setAddressName(rs.getString("addressname"));
            store.setAddressNumber(rs.getString("addressnumber"));
            store.setPostalCode(rs.getInt("postalcode"));
            stores.add(store);
        }// while

        stmnt.close();
        con.close();
        return stores;
    }// getAllStores

    public static List<Location> getAllLocations() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM location;";
        ResultSet rs = stmnt.executeQuery(query);
        List<Location> locations = new ArrayList<Location>();
        while(rs.next()){
            Location location = new Location();
            location.setLocationid(rs.getInt("locationid"));
            location.setAddressName(rs.getString("addressname"));
            location.setAddressNumber(rs.getString("addressnumber"));
            location.setPostalCode(rs.getInt("postalcode"));
            location.setStoreid(rs.getInt("storeid"));
            locations.add(location);
        }// while

        stmnt.close();
        con.close();
        return locations;
    }// getAllLocations

    public static List<Location> getLocationByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM location WHERE storeid=" + storeid +";";
        ResultSet rs = stmnt.executeQuery(query);
        List<Location> locations = new ArrayList<Location>();
        while(rs.next()){
            Location location = new Location();
            location.setLocationid(rs.getInt("locationid"));
            location.setAddressName(rs.getString("addressname"));
            location.setAddressNumber(rs.getString("addressnumber"));
            location.setPostalCode(rs.getInt("postalcode"));
            location.setStoreid(rs.getInt("storeid"));
            locations.add(location);
        }// while

        stmnt.close();
        con.close();
        return locations;
    }// getLocationByStoreid

    public static List<Location> getLocationByCity(String city) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        String query = "SELECT * FROM location WHERE storeid IN(SELECT storeid FROM store WHERE STRCMP(city,?)=0);";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setString(1, city);
        ResultSet rs = stmnt.executeQuery();
        List<Location> locations = new ArrayList<Location>();
        while(rs.next()){
            Location location = new Location();
            location.setLocationid(rs.getInt("locationid"));
            location.setAddressName(rs.getString("addressname"));
            location.setAddressNumber(rs.getString("addressnumber"));
            location.setPostalCode(rs.getInt("postalcode"));
            location.setStoreid(rs.getInt("storeid"));
            locations.add(location);
        }// while

        stmnt.close();
        con.close();
        return locations;
    }// getLocationByCity

    public static int getStoreidFromLocationid(int locationid) throws ClassNotFoundException, SQLException{
        int storeid;
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM store s INNER JOIN location l ON s.storeid=l.storeid WHERE locationid="+locationid+";";
        ResultSet rs = stmnt.executeQuery(query);
        if (rs.next()){
            storeid = rs.getInt("storeid");

            stmnt.close();
            con.close();
            return storeid;
        }// if

        stmnt.close();
        con.close();
        return -1;
    }// getStoreidFromCity

}// class
