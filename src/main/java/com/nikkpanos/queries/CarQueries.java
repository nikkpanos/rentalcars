package com.nikkpanos.queries;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.nikkpanos.beans.Car;
import com.nikkpanos.beans.FullCarInfoView;
import com.nikkpanos.utilities.ConnectionUtils;
import com.nikkpanos.beans.CarSize;
import com.nikkpanos.beans.CarType;

// Not extendable class. Will have only static methods
public class CarQueries {
    private static Connection con = null;

    private CarQueries(){}// This class is not meant to generate objects

    // Χρήση αμέσως μετά την επιλογή του αυτοκινήτου προς απόσυρση
    public static int deleteCar(int carid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        String query = "DELETE FROM car WHERE carid=?";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setInt(1, carid);
        int count = stmnt.executeUpdate();

        stmnt.close();
        con.close();
        return count;
    }// deleteCar

    // Χρήση αμέσως μετά την επιλογή όλων των απαραίτητων στοιχείων για την εισαγωγή νέας εγγραφής στον πίνακα car
    public static int insertNewCar(Car car) throws ClassNotFoundException, SQLException{
        String licensePlate = car.getLicensePlate();
        String model = car.getModel();
        int typeid = car.getTypeid();
        int sizeid = car.getSizeid();
        int seats = car.getSeats();
        int doors = car.getDoors();
        String conventional = car.getConventional();
        float pricePerDay = car.getPricePerDay();
        int storeid = car.getStoreid();

        int count;
        con = ConnectionUtils.getConnection();
        String query = "INSERT INTO car (licenseplate, model, typeid, sizeid, seats, doors, conventional, priceperday, storeid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement stmnt = con.prepareStatement(query);
        stmnt.setString(1, licensePlate);
        stmnt.setString(2, model);
        stmnt.setInt(3, typeid);
        stmnt.setInt(4, sizeid);
        stmnt.setInt(5, seats);
        stmnt.setInt(6, doors);
        stmnt.setString(7, conventional);
        stmnt.setFloat(8, pricePerDay);
        stmnt.setInt(9, storeid);

        count = stmnt.executeUpdate();

        stmnt.close();
        con.close();
        return count;
    }// insertNewCar

    // Χρήση αμέσως μετά την επιλογή όλων των ανανεωμένων στοιχείων υπάρχοντος αυτοκινήτου
    public static int updateCar(Car car) throws ClassNotFoundException, SQLException{
        int carid = car.getCarid();
        String licensePlate = car.getLicensePlate();
        String model = car.getModel();
        int seats = car.getSeats();
        int doors = car.getDoors();
        String conventional = car.getConventional();
        float pricePerDay = car.getPricePerDay();
        int storeid = car.getStoreid();

        int count;
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "UPDATE car SET licenseplate=\""+licensePlate+"\",model=\""+model+"\",seats="+seats+
                ",doors="+doors+",conventional=\""+conventional+"\",priceperday="+pricePerDay+
                ",storeid="+storeid+" WHERE carid="+carid+";";
        count = stmnt.executeUpdate(query);

        stmnt.close();
        con.close();
        return count;
    }// updateCar


    // Χρήση αμέσως μετά την επιλογή carid του οποίου θα τροποποιηθούν τα στοιχεία
    public final static Car getCarByCarid(int carid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM car WHERE carid="+carid+";";
        ResultSet rs = stmnt.executeQuery(query);
        if (rs.next()){
            Car car = new Car();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setTypeid(rs.getInt("typeid"));
            car.setSizeid(rs.getInt("sizeid"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            stmnt.close();
            con.close();
            return car;
        }// if

        stmnt.close();
        con.close();
        return null;
    }// getCarByCarid

    // Χρήση αμέσως μετά την επιλογή εμφάνισης όλων των διαθέσιμων αυτοκινήτων με βάση ημ/νιες και τοποθεσία
    public static List<FullCarInfoView> getFilteredAvailableCars(int deliveryLocationid, Timestamp deliveryDate, Timestamp  returnDate)
            throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        CallableStatement stmnt = con.prepareCall("{CALL get_filtered_available_cars(?, ?, ?)}");
        stmnt.setInt(1, deliveryLocationid);
        stmnt.setTimestamp(2, deliveryDate);
        stmnt.setTimestamp(3, returnDate);
        ResultSet rs = stmnt.executeQuery();
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getFilteredAvailableCars

    // Χρήση αμέσως μετά την επιλογή εμφάνισης όλων των διαθέσιμων αυτοκινήτων με βάση ημ/νίες, τοποθεσία ΚΑΙ τύπο αυτοκινήτου
    public static List<FullCarInfoView> getFilteredAvailableCarsByCarTypeid(int deliveryLocationid,
                                                                            Timestamp deliveryDate, Timestamp  returnDate, int carTypeid)throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        CallableStatement stmnt = con.prepareCall("{CALL get_filtered_available_cars_with_cartype(?, ?, ?, ?)}");
        stmnt.setInt(1, deliveryLocationid);
        stmnt.setTimestamp(2, deliveryDate);
        stmnt.setTimestamp(3, returnDate);
        stmnt.setInt(4,carTypeid);
        ResultSet rs = stmnt.executeQuery();
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getFilteredAvailableCarsByCarTypeid

    // Χρήση αμέσως μετά την επιλογή τροποποίησης στοιχείου/ων υπάρχοντος αυτοκινήτου
    public static List<FullCarInfoView> getFullCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM full_car_info WHERE storeid=" + storeid + ";";
        ResultSet rs = stmnt.executeQuery(query);
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getFullCarInfoByStoreid

    // Χρήση αμέσως μετά την επιλογή για εμφάνιση όλων των αυτοκινήτων ενός καταστήματος για επιλογή ενός προς απόσυρση
    // (δεν θέλω να εμφανιστούν αυτοκίνητα τα οποία έχουν επιλεγεί από πελάτες για ενοικίασης, ανεξαρτήτως ημερομηνιών)
    public static List<FullCarInfoView> getIdleCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM idle_car_info WHERE storeid=" + storeid + ";";
        ResultSet rs = stmnt.executeQuery(query);
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getIdleCarInfoByStoreid

    // Χρήση αμέσως μετά την επιλογή εμφάνισης όλων των αυτοκινήτων που είναι ενοικιασμένα την τωρινή στιγμή
    public static List<FullCarInfoView> getNotAvailableCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM not_available_car_info WHERE storeid=" + storeid + ";";
        ResultSet rs = stmnt.executeQuery(query);
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getNotAvailableCarInfoByStoreid

    // Χρήση αμέσως μετά την επιλογή για εμφάνιση όλων των ενεργών αυτοκινήτων όλης της εταιρείας
    public static List<FullCarInfoView> getAllFullCarInfo() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM full_car_info";
        ResultSet rs = stmnt.executeQuery(query);
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        while(rs.next()){
            FullCarInfoView car = new FullCarInfoView();
            car.setCarid(rs.getInt("carid"));
            car.setLicensePlate(rs.getString("licenseplate"));
            car.setModel(rs.getString("model"));
            car.setType(rs.getString("type"));
            car.setSize(rs.getString("size"));
            car.setSeats(rs.getInt("seats"));
            car.setDoors(rs.getInt("doors"));
            car.setConventional(rs.getString("conventional"));
            car.setPricePerDay(rs.getFloat("priceperday"));
            car.setStoreid(rs.getInt("storeid"));
            car.setCity(rs.getString("city"));
            cars.add(car);
        }// while

        stmnt.close();
        con.close();
        return cars;
    }// getAllFullCarInfo

    public static List<CarSize> getAllCarSizes() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM carsize;";
        ResultSet rs = stmnt.executeQuery(query);
        List<CarSize> sizes = new ArrayList<CarSize>();
        while (rs.next()){
            CarSize carSize = new CarSize();
            carSize.setSizeid(rs.getInt("sizeid"));
            carSize.setSize(rs.getString("size"));
            sizes.add(carSize);
        }// while

        stmnt.close();
        con.close();
        return sizes;
    }// getAllCarSizes

    public static List<CarType> getAllCarTypes() throws ClassNotFoundException, SQLException{
        con = ConnectionUtils.getConnection();
        Statement stmnt = con.createStatement();
        String query = "SELECT * FROM cartype;";
        ResultSet rs = stmnt.executeQuery(query);
        List<CarType> types = new ArrayList<CarType>();
        while (rs.next()){
            CarType carType = new CarType();
            carType.setTypeid(rs.getInt("typeid"));
            carType.setType(rs.getString("type"));
            types.add(carType);
        }// while

        stmnt.close();
        con.close();
        return types;
    }// getAllCarTypes

}// class
