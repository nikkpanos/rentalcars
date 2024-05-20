package com.nikkpanos.utilities;

import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.nikkpanos.beans.*;
import com.nikkpanos.queries.*;

public final class FunctionUtils {

    private FunctionUtils(){}// This class is not meant to generate objects

/*
 ==============================================================================================================================
=====================ΜΕΘΟΔΟΙ ΠΟΥ ΧΡΗΣΙΜΟΠΟΙΟΥΝ ΤΑ ΕΤΟΙΜΑ QUERIES ΓΙΑ ΝΑ ΕΜΦΑΝΙΣΟΥΝ ΣΤΗΝ ΟΘΟΝΗ ΚΟΝΣΟΛΑΣ=========================
=====================================ΤΑ RESULT SET ΤΩΝ QUERIES ΜΕ ΟΡΓΑΝΩΜΕΝΟ ΤΡΟΠΟ=============================================
 ==============================================================================================================================
 */

    public static void showAllCarTypes() throws ClassNotFoundException, SQLException{
        List<CarType> types = new ArrayList<CarType>();
        types = CarQueries.getAllCarTypes();
        System.out.println("Λίστα διαθέσιμων τύπων αυτοκινήτου: ");
        System.out.println("ID\tΤΥΠΟΣ");
        for(CarType type: types){
            int typeid = type.getTypeid();
            String typeName = type.getType();
            System.out.println(typeid +"\t"+ typeName);
        }// for
        System.out.println();
    }// showAllCarTypes

    public static void showAllCarSizes() throws ClassNotFoundException, SQLException{
        List<CarSize> sizes = new ArrayList<CarSize>();
        sizes = CarQueries.getAllCarSizes();
        System.out.println("Λίστα διαθέσιμων μεγεθών αυτοκινήτου: ");
        System.out.println("ID\tΜΕΓΕΘΟΣ");
        for(CarSize size: sizes){
            int sizeid = size.getSizeid();
            String sizeName = size.getSize();
            System.out.println(sizeid + "\t" + sizeName);
        }// for
        System.out.println();
    }// showAllCarSizes

    public static void showLocationByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<Location> locations = new ArrayList<Location>();
        locations = StoreQueries.getLocationByStoreid(storeid);
        System.out.println("Λίστα διαθέσιμων τοποθεσιών για το κατάστημα με ID " + storeid + ": ");
        System.out.println("ID\tΔΙΕΥΘΥΝΣΗ\t\tΤ.Κ.");
        for (Location location: locations){
            int locationid = location.getLocationid();
            String addressName = location.getAddressName();
            String addressNumber = location.getAddressNumber();
            String address = addressName +" "+ addressNumber;
            int postalCode = location.getPostalCode();
            System.out.print(locationid +"\t" + address);
            if (address.length() <= 11) System.out.print("\t");
            System.out.println("\t"+ postalCode);
        }// for
        System.out.println();
    }// showAllLocations

    public static void showLocationByCity(String city) throws ClassNotFoundException, SQLException{
        List<Location> locations = new ArrayList<Location>();
        locations = StoreQueries.getLocationByCity(city);
        System.out.println("Λίστα διαθέσιμων τοποθεσιών για την πόλη " + city + ": ");
        System.out.println("ID\tΔΙΕΥΘΥΝΣΗ\t\tΤ.Κ.");
        for (Location location: locations){
            int locationid = location.getLocationid();
            String addressName = location.getAddressName();
            String addressNumber = location.getAddressNumber();
            String address = addressName +" "+ addressNumber;
            int postalCode = location.getPostalCode();
            System.out.print(locationid +"\t" + address);
            if (address.length() <= 11) System.out.print("\t");
            System.out.println("\t"+ postalCode);
        }// for
        System.out.println();
    }// showLocationByCity

    public static void showAllStores() throws ClassNotFoundException, SQLException{
        List<Store> stores = new ArrayList<Store>();
        stores = StoreQueries.getAllStores();
        System.out.println("Λίστα διαθέσιμων καταστημάτων: ");
        System.out.println("ID\tΠΟΛΗ\t\tΔΙΕΥΘΥΝΣΗ\t\tΤ.Κ.");
        for (Store store: stores){
            int storeid = store.getStoreid();
            String city = store.getCity();
            String addressName = store.getAddressName();
            String addressNumber = store.getAddressNumber();
            String address = addressName + " " + addressNumber;
            int postalCode = store.getPostalCode();
            System.out.print(storeid + "\t" + city + "\t");
            if (city.length()<11) System.out.print("\t");
            System.out.print(address + "\t");
            if (address.length()<12) System.out.print("\t");
            System.out.println(postalCode);
        }// for
        System.out.println();
    }// showAllStores

    public static void showAllFullCarInfo() throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getAllFullCarInfo();
        System.out.println("Λίστα όλων των ενεργών αυτοκινήτων της εταιρείας (ταξινομημένα ανά κατάστημα): ");
        printFullCarInfoView(cars);
    }// showAllFullCarInfo

    public static void showFullCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getFullCarInfoByStoreid(storeid);
        System.out.println("Λίστα όλων των αυτοκινήτων που ανήκουν στο κατάστημα με ID "+storeid+": ");
        printFullCarInfoView(cars);
    }// showFullCarInfoByStoreid

    public static void showNotAvailableCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getNotAvailableCarInfoByStoreid(storeid);
        System.out.println("Λίστα των αυτοκινήτων του καταστήματος με ID "+storeid+", τα οποία αυτή τη στιγμή δεν είναι διαθέσιμα: ");
        printFullCarInfoView(cars);
    }// showNotAvailableCarInfoByStoreid

    public static void showFilteredAvailableCars(int deliveryLocationid, Timestamp deliveryDate, Timestamp  returnDate)
            throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getFilteredAvailableCars(deliveryLocationid, deliveryDate, returnDate);
        System.out.println("Λίστα όλων των αυτοκινήτων που είναι διαθέσιμα βάση των φίλτρων που εισάγατε: ");
        printFullCarInfoView(cars);
    }// showFilteredAvailableCars

    public static void showFilteredAvailableCarsByCarTypeid(int deliveryLocationid, Timestamp deliveryDate,
                                                            Timestamp  returnDate, int carTypeid)throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getFilteredAvailableCarsByCarTypeid(deliveryLocationid, deliveryDate, returnDate, carTypeid);
        System.out.println("Λίστα όλων των αυτοκινήτων που είναι διαθέσιμα βάση των φίλτρων που εισάγατε: ");
        printFullCarInfoView(cars);
    }// showFilteredAvailableCarsByCarTypeid

    public static void showIdleCarInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<FullCarInfoView> cars = new ArrayList<FullCarInfoView>();
        cars = CarQueries.getIdleCarInfoByStoreid(storeid);
        System.out.println("Λίστα όλων των αυτοκινήτων που είναι διαθέσιμα βάση των φίλτρων που εισάγατε: ");
        printFullCarInfoView(cars);
    }// showIdleCarInfoByStoreid

    public static void showAllCustomers() throws ClassNotFoundException, SQLException{
        List<Customer> customers = new ArrayList<Customer>();
        customers = CustomerQueries.getAllCustomers();
        System.out.println("Λίστα με όλους τους πελάτες: ");
        System.out.println("ΑΔΕΙΑ ΟΔΗΓΗΣΗΣ\t\tONOMATEΠΩΝΥΜΟ\t\t\t\t\tΕΜΑΙL\t\t\t\t\t\tΚΙΝΗΤΟ\t\tΣΤΑΘΕΡΟ");
        for(Customer customer: customers){
            String driverLicense = customer.getDriverLicense();
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            String fullName = firstName +" "+ lastName;
            String email = customer.getEmail();
            String cellphoneNumber = customer.getCellphoneNumber();
            String homephoneNumber = customer.getHomephoneNumber();
            System.out.print(driverLicense);
            if (driverLicense.length()<4) System.out.print("\t");
            if (driverLicense.length()<8) System.out.print("\t");
            if (driverLicense.length()<12) System.out.print("\t");
            System.out.print("\t\t"+fullName);
            if (fullName.length() < 16) System.out.print("\t");
            if (fullName.length() < 20) System.out.print("\t");
            System.out.print("\t\t\t"+email);
            if (email.length() < 16) System.out.print("\t");
            if (email.length() < 20) System.out.print("\t");
            if (email.length() < 24) System.out.print("\t");
            System.out.print("\t"+cellphoneNumber+"\t"+homephoneNumber);
            System.out.println();
        }// for
        System.out.println();
    }// showAllCustomers

    public static void showFullRentInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<FullRentInfoView> rents = new ArrayList<FullRentInfoView>();
        rents = RentQueries.getFullRentInfoByStoreid(storeid);
        System.out.println("Λίστα όλων των καταχωρημένων ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 1/3): ");
        System.out.println("ID\tΑΔΕΙΑ ΟΔΗΓΗΣΗΣ ΠΕΛΑΤΗ\tΟΝΟΜΑΤΕΠΩΝΥΜΟ\t\t\tΚΙΝΗΤΟ\t\tID ΑΥΤΟΚΙΝΗΤΟΥ\tΜΟΝΤΕΛΟ\t\t\tΠΟΛΗ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            String customerDriverLicense = rent.getCustomerDriverLicense();
            String firstName = rent.getFirstName();
            String lastName = rent.getLastName();
            String fullName = firstName + " " + lastName;
            String cellphoneNumber = rent.getCellphoneNumber();
            int carid = rent.getCarid();
            String model = rent.getModel();
            String city = rent.getCity();
            System.out.print(rentid+"\t"+customerDriverLicense+"\t\t\t\t"+fullName);
            if(fullName.length() < 16) System.out.print("\t");
            if(fullName.length() < 20) System.out.print("\t");
            System.out.print("\t"+cellphoneNumber+"\t"+carid+"\t\t\t\t"+
                    model+"\t"+city);
            System.out.println();
        }// for

        System.out.println();
        System.out.println("Λίστα όλων των καταχωρημένων ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 2/3): ");
        System.out.println("ID\tID ΤΟΠΟΘΕΣΙΑΣ ΠΑΡΑΛΑΒΗΣ\tΗΜ/ΝΙΑ-ΩΡΑ ΠΑΡΑΛΑΒΗΣ\tΠΑΡΑΔΟΘΗΚΕ\tID ΤΟΠΟΘΕΣΙΑΣ ΠΑΡΑΔΟΣΗΣ\tΗΜ/ΝΙΑ-ΩΡΑ ΠΑΡΑΔΟΣΗΣ\t"+
                "ΕΠΙΣΤΡΑΦΗΚΕ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            int deliveryLocationid = rent.getDeliveryLocationid();
            Timestamp deliveryDate = rent.getDeliveryDate();
            String delivered = rent.getDelivered();
            int returnLocationid = rent.getReturnLocationid();
            Timestamp returnDate = rent.getReturnDate();
            String returned = rent.getReturned();
            System.out.print(rentid+"\t"+deliveryLocationid+"\t\t\t\t\t\t"+deliveryDate+"\t"+delivered+"\t\t\t"+
                    returnLocationid+"\t\t\t\t\t\t"+returnDate+"\t"+returned);
            System.out.println();
        }// for

        System.out.println();
        System.out.println("Λίστα όλων των καταχωρημένων ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 3/3): ");
        System.out.println("ID\tΤΙΜΗ ΑΝΑ ΗΜΕΡΑ\tΗΜΕΡΕΣ\tΣΥΝΟΛΙΚΟ ΚΟΣΤΟΣ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            float pricePerDay = rent.getPricePerDay();
            float days = rent.getDays();
            float totalPrice = rent.getTotalPrice();
            System.out.print(rentid+"\t"+pricePerDay+"\t\t\t"+days+"\t"+totalPrice);
            System.out.println();
        }// for
        System.out.println();
    }// showFullRentInfoByStoreid

    public static void showPendingRentInfoByStoreid(int storeid) throws ClassNotFoundException, SQLException{
        List<FullRentInfoView> rents = new ArrayList<FullRentInfoView>();
        rents = RentQueries.getPendingRentInfoByStoreid(storeid);
        if (rents.isEmpty()) return;
        System.out.println("=================================================================================");
        System.out.println("=================================== ΕΙΔΟΠΟΙΗΣΗ ==================================");
        System.out.println("=================================================================================\n");
        System.out.println("Λίστα όλων των εκκρεμών ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 1/3): ");
        System.out.println("ID\tΑΔΕΙΑ ΟΔΗΓΗΣΗΣ ΠΕΛΑΤΗ\tΟΝΟΜΑΤΕΠΩΝΥΜΟ\t\t\tΚΙΝΗΤΟ\t\tID ΑΥΤΟΚΙΝΗΤΟΥ\tΜΟΝΤΕΛΟ\t\t\tΠΟΛΗ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            String customerDriverLicense = rent.getCustomerDriverLicense();
            String firstName = rent.getFirstName();
            String lastName = rent.getLastName();
            String fullName = firstName + " " + lastName;
            String cellphoneNumber = rent.getCellphoneNumber();
            int carid = rent.getCarid();
            String model = rent.getModel();
            String city = rent.getCity();
            System.out.print(rentid+"\t"+customerDriverLicense+"\t\t\t\t"+fullName);
            if(fullName.length() < 16) System.out.print("\t");
            if(fullName.length() < 20) System.out.print("\t");
            System.out.print("\t"+cellphoneNumber+"\t"+carid+"\t\t\t\t"+
                    model+"\t"+city);
            System.out.println();
        }// for

        System.out.println();
        System.out.println("Λίστα όλων των εκκρεμών ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 2/3): ");
        System.out.println("ID\tID ΤΟΠΟΘΕΣΙΑΣ ΠΑΡΑΛΑΒΗΣ\tΗΜ/ΝΙΑ-ΩΡΑ ΠΑΡΑΛΑΒΗΣ\tΠΑΡΑΔΟΘΗΚΕ\tID ΤΟΠΟΘΕΣΙΑΣ ΠΑΡΑΔΟΣΗΣ\tΗΜ/ΝΙΑ-ΩΡΑ ΠΑΡΑΔΟΣΗΣ\t"+
                "ΕΠΙΣΤΡΑΦΗΚΕ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            int deliveryLocationid = rent.getDeliveryLocationid();
            Timestamp deliveryDate = rent.getDeliveryDate();
            String delivered = rent.getDelivered();
            int returnLocationid = rent.getReturnLocationid();
            Timestamp returnDate = rent.getReturnDate();
            String returned = rent.getReturned();
            System.out.print(rentid+"\t"+deliveryLocationid+"\t\t\t\t\t\t"+deliveryDate+"\t"+delivered+"\t\t\t"+
                    returnLocationid+"\t\t\t\t\t\t"+returnDate+"\t"+returned);
            System.out.println();
        }// for

        System.out.println();
        System.out.println("Λίστα όλων των εκκρεμών ενοικιάσεων που αφορούν στο κατάστημα με ID " + storeid + "(Σελίδα 3/3): ");
        System.out.println("ID\tΤΙΜΗ ΑΝΑ ΗΜΕΡΑ\tΗΜΕΡΕΣ\tΣΥΝΟΛΙΚΟ ΚΟΣΤΟΣ");
        for(FullRentInfoView rent: rents){
            int rentid = rent.getRentid();
            float pricePerDay = rent.getPricePerDay();
            float days = rent.getDays();
            float totalPrice = rent.getTotalPrice();
            System.out.print(rentid+"\t"+pricePerDay+"\t\t\t"+days+"\t"+totalPrice);
            System.out.println("\n");
        }// for
    }// showPendingRentInfoByStoreid

/*
================================================================================================================================
======================================================ΒΟΗΘΗΤΙΚΕΣ ΜΕΘΟΔΟΙ========================================================
================================================================================================================================
 */

    // Βοηθητικοί μέθοδος για τις μεθόδους που εμφανίζουν στην οθόνη στοιχεία αντικειμένων της κλάσης FullCarInfoView
    private static void printFullCarInfoView(List<FullCarInfoView> cars){
        System.out.println("ID\tΠΙΝΑΚΙΔΑ\tΜΟΝΤΕΛΟ\t\t\t\tΤΥΠΟΣ\t\t\tΜΕΓΕΘΟΣ\t\tΚΑΘΙΣΜΑΤΑ\tΠΟΡΤΕΣ\tΣΥΜΒΑΤΙΚΟ\tΤΙΜΗ ΑΝΑ ΗΜΕΡΑ\t"+
                           "ΠΟΛΗ\t\tID ΚΑΤΑΣΤΗΜΑΤΟΣ");
        for (FullCarInfoView car: cars){
            int carid = car.getCarid();
            String licensePlate = car.getLicensePlate();
            String model = car.getModel();
            String type = car.getType();
            String size = car.getSize();
            int seats = car.getSeats();
            int doors = car.getDoors();
            String conventional = car.getConventional();
            float pricePerDay = car.getPricePerDay();
            String city = car.getCity();
            int storeid = car.getStoreid();
            System.out.print(carid+"\t"+licensePlate+"\t\t"+model);
            if (model.length() <16) System.out.print("\t");
            if (model.length()<12) System.out.print("\t");
            System.out.print("\t"+type);
            if (type.length()<12) System.out.print("\t");
            System.out.print("\t"+size);
            if (size.length() <= 7) System.out.print("\t");
            System.out.print("\t"+seats+"\t\t\t"+doors+"\t\t"+conventional+"\t\t\t"+pricePerDay+"\t\t\t"+city);
            if (city.length()<8) System.out.print("\t");
            System.out.print("\t"+storeid);
            System.out.println();
        }// for
        System.out.println();
    }// printFullCarInfoView

    // Βοηθητική μέθοδος για μετατροπή από ακέραιους (που αναπαριστούν χρονιά, μήνα κλπ), σε αντικείμενο τύπου Timestamp
    public static Timestamp integersToTimestamp(int iyear, int imonth, int iday, int ihour, int iminutes) throws java.text.ParseException{
        String year, month, day, hour, minutes;
        year = String.valueOf(iyear);
        if (imonth<10) month = "0" + String.valueOf(imonth);
        else month = String.valueOf(imonth);
        if (iday<10) day = "0" + String.valueOf(iday);
        else day = String.valueOf(iday);
        if (ihour<10) hour = "0" + String.valueOf(ihour);
        else hour = String.valueOf(ihour);
        if (iminutes<10) minutes = "0" + String.valueOf(iminutes);
        else minutes = String.valueOf(iminutes);
        String dateString = year +"-"+ month +"-"+ day +" "+ hour +":"+ minutes +":00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(dateString);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp;
    }// inputToTimestamp

/*
==========================================================================================================================
=======================ΜΕΘΟΔΟΙ ΠΟΥ ΕΧΟΥΝ ΚΩΔΙΚΑ ΠΟΥ ΔΕΝ ΗΘΕΛΑ ΝΑ ΒΡΙΣΚΕΤΑΙ ΣΤΗΝ ΜΑΙΝ ΛΟΓΩ ΜΕΓΑΛΟΥ ΟΓΚΟΥ===================
============ΧΡΗΣΗ ΓΙΑ ΕΙΣΟΔΟ ΤΙΜΩΝ ΑΠΟ ΤΟΝ ΧΡΗΣΤΗ ΜΕ ΣΚΟΠΟ ΤΗΝ ΑΛΛΑΓΗ ΣΤΟΙΧΕΙΩΝ ΥΠΑΡΧΟΝΤΩΝ ΚΑΤΑΧΩΡΗΣΕΩΝ ΤΗΣ ΒΑΣΗΣ=========
==========================================================================================================================
*/

    public static Car changeCarObject(Car car, Scanner in){
        boolean goodInput;
        System.out.println("Θα θέλατε να αλλάξετε τον αριθμό πινακίδας;\n1. Ναι\n2. Όχι");
        int change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε την νέα πινακίδα: ");
            String licensePlate = null;
            goodInput = false;
            while(!goodInput){
                try {
                    licensePlate = in.nextLine();
                    goodInput = true;
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Κάτι πήγε στραβά. Παρακαλώ εισάγετε ξανά: ");
                }// try-catch
            }// while
            car.setLicensePlate(licensePlate);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το όνομα του μοντέλου;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο όνομα μοντέλου: ");
            String model = null;
            goodInput = false;
            while(!goodInput){
                try {
                    model = in.nextLine();
                    goodInput = true;
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Κάτι πήγε στραβά. Παρακαλώ εισάγετε ξανά: ");
                }// try-catch
            }// while
            car.setModel(model);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε τον αριθμό θέσεων;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο αριθμό θέσεων: ");
            int seats = -1;
            while(seats <= 0){
                try {
                    seats = in.nextInt();
                    in.nextLine();
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Κάτι πήγε στραβά. Παρακαλώ εισάγετε ξανά: ");
                }// try-catch
            }// while
            car.setSeats(seats);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε τον αριθμό θυρών;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο αριθμό θυρών: ");
            int doors = -1;
            while(doors <= 0){
                try {
                    doors = in.nextInt();
                    in.nextLine();
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Κάτι πήγε στραβά. Παρακαλώ εισάγετε ξανά: ");
                }// try-catch
            }// while
            car.setDoors(doors);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε την τιμή ανά ημέρα;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέα τιμή ανά ημέρα: ");
            float pricePerDay = -1;
            while(pricePerDay <= 0){
                try {
                    pricePerDay = in.nextFloat();
                    in.nextLine();
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Κάτι πήγε στραβά. Παρακαλώ εισάγετε ξανά: ");
                }// try-catch
            }// while
            car.setPricePerDay(pricePerDay);
        }// if

        return car;
    }// changeCarObject

    public static Customer changeCustomerObject(Customer customer, Scanner in){
        System.out.println("Θα θέλατε να αλλάξετε τον αριθμό διπλώματος;\n1. Ναι\n2. Όχι");
        int change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο αριθμό διπλώματος: ");
            String driverLicense = in.nextLine();
            customer.setDriverLicense(driverLicense);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το όνομα;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο όνομα: ");
            String firstName = in.nextLine();
            customer.setFirstName(firstName);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το επώνυμο;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο επώνυμο: ");
            String lastName = in.nextLine();
            customer.setLastName(lastName);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το email;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο email: ");
            String email = in.nextLine();
            customer.setEmail(email);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το κινητό;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο κινητό: ");
            String cellphoneNumber = in.nextLine();
            customer.setCellphoneNumber(cellphoneNumber);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε το τηλέφωνο κατοικίας;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            System.out.println("Εισάγετε νέο τηλέφωνο κατοικίας: ");
            String homephoneNumber = in.nextLine();
            customer.setHomephoneNumber(homephoneNumber);
        }// if

        return customer;
    }// changeCustomerObject

    public static Rent changeRentObject(Rent rent, Scanner in, int storeOption)
            throws ClassNotFoundException, SQLException, java.text.ParseException{
        System.out.println("Θα θέλατε να αλλάξετε την τοποθεσία παραλαβής;\n1. Ναι\n2. Όχι");
        int change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            FunctionUtils.showLocationByStoreid(storeOption);
            int deliveryLocationid = -1;
            while (deliveryLocationid<1){
                try {
                    System.out.println("Εισάγετε ID τοποθεσίας παραλαβής: ");
                    deliveryLocationid = in.nextInt();
                    in.nextLine();
                    if (deliveryLocationid<1) System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Παρακαλώ προσπαθήστε ξανά. ");
                }// try - catch
            }// while
            rent.setDeliveryLocationid(deliveryLocationid);
        }// if

        System.out.println("Θα θέλατε να αλλάξετε την τοποθεσία παράδοσης;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            FunctionUtils.showLocationByStoreid(storeOption);
            int returnLocationid = -1;
            while (returnLocationid<1){
                try {
                    System.out.println("Εισάγετε ID τοποθεσίας παράδοσης: ");
                    returnLocationid = in.nextInt();
                    in.nextLine();
                    if (returnLocationid<1) System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                }// try - catch
            }// while
            rent.setReturnLocationid(returnLocationid);
        }// if

        Timestamp curDeliveryDate = rent.getDeliveryDate();
        System.out.println("Καταχωρημένη ημερομηνία παραλαβής είναι η " + curDeliveryDate);
        System.out.println("Θα θέλατε να αλλάξετε την ημερομηνία παραλαβής;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            int year, month, day, hour, minute;
            year = month = day = hour = minute = -1;
            System.out.println("Εισαγωγή στοιχείων που αφορούν στον χρόνο παραλαβής.");
            while(year<2024 || month<1 || month>12 || day<1 || day>31 || hour<0 || hour>23 || minute<0 || minute>59){
                try {
                    System.out.println("Εισάγετε έτος (π.χ. 2024): "); year = in.nextInt();
                    System.out.println("Εισάγετε μήνα (σε αριθμό): "); month = in.nextInt();
                    System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): "); day = in.nextInt();
                    System.out.println("Εισάγετε ώρα (0 έως 23): "); hour = in.nextInt();
                    System.out.println("Εισάγετε λεπτά (0 έως 59): "); minute = in.nextInt();
                    in.nextLine();
                    if (year<2024 || month<1 || month>12 || day<1 || day>31 || hour<0 || hour>23 || minute<0 || minute>59)
                        System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                }// try-catch
            }// while
            Timestamp deliveryDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
            rent.setDeliveryDate(deliveryDate);
        }// if

        Timestamp curReturnDate = rent.getReturnDate();
        System.out.println("Καταχωρημένη ημερομηνία παράδοσης είναι η " + curReturnDate);
        System.out.println("Θα θέλατε να αλλάξετε την ημερομηνία παράδοσης;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            int year, month, day, hour, minute;
            year = month = day = hour = minute = -1;
            System.out.println("Εισαγωγή στοιχείων που αφορούν στον χρόνο παράδοσης.");
            while(year<2024 || month<1 || month>12 || day<1 || day>31 || hour<0 || hour>23 || minute<0 || minute>59){
                try {
                    System.out.println("Εισάγετε έτος (π.χ. 2024): "); year = in.nextInt();
                    System.out.println("Εισάγετε μήνα (σε αριθμό): "); month = in.nextInt();
                    System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): "); day = in.nextInt();
                    System.out.println("Εισάγετε ώρα (0 έως 23): "); hour = in.nextInt();
                    System.out.println("Εισάγετε λεπτά (0 έως 59): "); minute = in.nextInt();
                    in.nextLine();
                    if (year<2024 || month<1 || month>12 || day<1 || day>31 || hour<0 || hour>23 || minute<0 || minute>59)
                        System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                } catch (Exception e) {
                    in.nextLine();
                    System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                }// try-catch
            }// while
            Timestamp returnDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
            rent.setReturnDate(returnDate);
        }// if

        String curDeliveredState = rent.getDelivered();
        System.out.println("Η παρούσα κατάσταση παραλαβής είναι καταχωρημένη ως " + curDeliveredState);
        System.out.println("Θα θέλατε να αλλάξετε την κατάσταση παραλαβής;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            String delivered;
            if (curDeliveredState.equals("Ναι")) delivered = "Όχι";
            else delivered = "Ναι";
            rent.setDelivered(delivered);
        }// if

        String curReturnedState = rent.getReturned();
        System.out.println("Η παρούσα κατάσταση παράδοσης είναι καταχωρημένη ως " + curReturnedState);
        System.out.println("Θα θέλατε να αλλάξετε την κατάσταση παράδοσης;\n1. Ναι\n2. Όχι");
        change = -1;
        while(change != 1 && change != 2){
            try {
                change = in.nextInt();
                in.nextLine();
                if (change != 1 && change != 2) System.out.println("Εισάγετε 1 ή 2: ");
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
            }// try-catch
        }// while
        if (change == 1){
            String returned;
            if (curReturnedState.equals("Ναι")) returned = "Όχι";
            else returned = "Ναι";
            rent.setReturned(returned);
        }// if

        return rent;
    }// changeRentObject
}// class

