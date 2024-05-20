package com.nikkpanos.queries;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.nikkpanos.beans.Customer;
import com.nikkpanos.utilities.ConnectionUtils;

// Not extendable class. Will have only static methods
public final class CustomerQueries {
    private static Connection con = null;

    private CustomerQueries(){}// This class is not meant to generate objects

    // Χρήση αμέσως μετά την επιλογή όλων των στοιχείων του νέου πελάτη που θα καταχωρηθεί
    public static int insertNewCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        String driverLicense = customer.getDriverLicense();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String email = customer.getEmail();
        String cellphoneNumber = customer.getCellphoneNumber();
        String homephoneNumber = customer.getHomephoneNumber();

        int count;
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "INSERT INTO customer VALUES(\""+driverLicense+"\",\""+firstName+"\",\""+lastName+"\",\""+email+"\",\""+
                cellphoneNumber+"\",\""+homephoneNumber+"\");";
        count = stmnt.executeUpdate(query);

        stmnt.close();
        con.close();
        return count;
    }// insertNewCustomer

    // Χρήση αμέσως μετά την καταχώριση όλων των στοιχείων του προς τροποποίηση πελάτη
    public static int updateCustomer(Customer customer, String oldDriverLicense) throws ClassNotFoundException, SQLException{
        String newDriverLicense = customer.getDriverLicense();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String email = customer.getEmail();
        String cellphoneNumber = customer.getCellphoneNumber();
        String homephoneNumber = customer.getHomephoneNumber();

        int count;
        con = ConnectionUtils.getConnection();
        String query = "UPDATE customer SET driverlicense=?, firstname=?, lastname=?, email=?, cellphoneNumber=?, homephonenumber=? WHERE driverlicense=?;";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setString(1, newDriverLicense);
        stmnt.setString(2, firstName);
        stmnt.setString(3, lastName);
        stmnt.setString(4, email);
        stmnt.setString(5, cellphoneNumber);
        stmnt.setString(6, homephoneNumber);
        stmnt.setString(7, oldDriverLicense);
        count = stmnt.executeUpdate();

        return count;
    }// updateCustomer

    // Χρήση αμέσως μετά την επιλογή τροποίησης στοιχείων υπάρχοντος πελάτη
    public static List<Customer> getAllCustomers() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM customer";
        ResultSet rs = stmnt.executeQuery(query);
        List<Customer> customers = new ArrayList<Customer>();
        while(rs.next()){
            Customer customer = new Customer();
            customer.setDriverLicense(rs.getString("driverlicense"));
            customer.setFirstName(rs.getString("firstname"));
            customer.setLastName(rs.getString("lastname"));
            customer.setEmail(rs.getString("email"));
            customer.setCellphoneNumber(rs.getString("cellphonenumber"));
            customer.setHomephoneNumber(rs.getString("homephonenumber"));
            customers.add(customer);
        }// while

        stmnt.close();
        con.close();
        return customers;
    }// getAllCustomers

    // Χρήση αμέσως μετά την επιλογή συγκεκριμένου πελάτη για τροποποίηση στοιχείου/ων του
    public static Customer getCustomerByDriverLicense(String driverLicense) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM customer WHERE driverlicense=\"" + driverLicense +"\";";
        ResultSet rs = stmnt.executeQuery(query);
        if(rs.next()){
            Customer customer = new Customer();
            customer.setDriverLicense(rs.getString("driverlicense"));
            customer.setFirstName(rs.getString("firstname"));
            customer.setLastName(rs.getString("lastname"));
            customer.setEmail(rs.getString("email"));
            customer.setCellphoneNumber(rs.getString("cellphonenumber"));
            customer.setHomephoneNumber(rs.getString("homephonenumber"));
            stmnt.close();
            con.close();
            return customer;
        }// if

        stmnt.close();
        con.close();
        return null;
    }// getCustomerByDriverLicense

}// class
