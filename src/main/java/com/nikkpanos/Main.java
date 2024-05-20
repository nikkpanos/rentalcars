package com.nikkpanos;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Scanner;

import com.nikkpanos.queries.*;
import com.nikkpanos.utilities.FunctionUtils;
import com.nikkpanos.beans.*;

/*
*   storeOption --> Κρατάει το ID του καταστήματος του χρήστη (ζητείται από τον χρήστη ΜΟΝΟ κατά την έναρξη του προγράμματος
*   mainMenu --> Καθορίζει την επιλογή του κεντρικού-αρχικού μενού
*   subMenu  --> Καθορίζει την επιλογή του υπομενού που εμφανίζεται μετά την επιλογή Νο1 του κεντρικού μενού
*   Εκτός από την επιλογή Νο1 (subMenu), όλες οι άλλες επιλογές του mainMenu δεν έχουν διακλαδώσεις
*   Exceptions που δημιουργούνται κατά την είσοδο δεδομένων από τον χρήστη διαχειρίζονται επί τόπου
*   Λοιπά Exceptions διακόπτουν τη ροή του προγράμματος
*   Queries προς την βάση και εμφάνιση αποτελεσμάτων των queries στην οθόνη εκτελούνται στην main μόνο μέσω static μεθόδων
*   Το πακέτο beans περιέχει τις κλάσεις που αντιπροσωπεύουν τους πίνακες της ΒΔ
*   Το πακέτο queries περιέχει όλα τα queries που χρησιμοποιούνται στο πρόγραμμα. Αν το query είναι insert/update/delete
*       τότε επιστρέφει τον αριθμό των εγγραφών που επηρεάστηκαν, ενώ αν είναι query που φέρνει result set, τότε στο
*       πρόγραμμα επιστρέφεται ένα αντικείμενο ή λίστα αντικειμένων beans, όπου θα περιέχονται τα αποτελέσματα του
*       result set.
*   Το πακέτο utilities περιέχει 2 πράγματα. Πρώτον την κλάση η οποία δημιουργεί τη σύνδεση με τη ΒΔ. Δεύτερον,
*       περιέχει άλλη μια κλάση όπυ βρίσκονται οι μέθοδοι που χρησιμοποιούνται για την εμφάνιση των αποτελεσμάτων των queries
*       στην κονσόλα, οι μέθοδοι που καλούνται όταν θέλουμε να βάλουμε τον χρήστη να εισάγει τιμές και εν τέλη να
*       τροποποιήσει ένα αντικείμενο, και τέλος κάποιες βοηθητικές μεθόδους (πχ δημιουργία Timestamp χρησιμοποιώντας integers
*   Η σύνδεση με τη ΒΔ δημιουργείται και κλείνει εντός καθενός από τις μεθόδους που υπάρχουν στις κλάσεις του queries
*   Όλο το πρόγραμμα χρησιμοποιεί έναν Scanner, ο οποίος κλείνει μόνο όταν ο χρήστης επιλέξει να τερματίσει το πρόγραμμα.
*       Όπου χρειάζεται Scanner αντικείμενο σε μέθοδο εκτός της main, της περνάω το αντικείμενο Scanner της main ως όρισμα.
*/

public class Main {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);
            int storeOption, mainMenu;
            System.out.println("Γεια σας! Πριν ξεκινήσουμε, πρέπει να επιλέξετε το κατάστημα στο οποίο εργάζεστε.\n\n");
            FunctionUtils.showAllStores();
            System.out.println("Παρακαλώ πληκτρολογήστε το ID του καταστήματος: ");
            try {
                storeOption = in.nextInt();
                in.nextLine();
            } catch (Exception e) {
                in.nextLine();
                storeOption = 0;
            }// try-catch

            while (storeOption < 1 || storeOption > 4) {
                System.out.println("Πληκτρολογήσατε ID που δεν υπάρχει...Παρακαλώ προσπαθήστε ξανά: ");
                try {
                    storeOption = in.nextInt();
                    in.nextLine();
                } catch (Exception e) {
                    in.nextLine();
                    storeOption = 0;
                }// try-catch
            }// while
            System.out.println("================================ ΚΑΛΩΣ ΗΡΘΑΤΕ ================================");
            System.out.println("\n");
            FunctionUtils.showPendingRentInfoByStoreid(storeOption);
            System.out.println();
            mainMenu = 1;
            while (mainMenu != 0) {
                System.out.println("AΡΧΙΚΟ ΜΕΝΟΥ");
                System.out.println("1. Εισαγωγή νέας ή ενημέρωση παλιάς εγγραφής που αφορά σε αυτοκίνητο, πελάτη ή ενοικίαση");
                System.out.println("2. Εμφάνιση όλων των αυτοκινήτων της εταιρείας προς ενοικίαση (διαθέσιμα και μη)");
                System.out.println("3. Εμφάνιση ενοικιασμένων (ΟΧΙ διαθέσιμων) αυτοκινήτων του καταστήματος αυτή τη στιγμή");
                System.out.println("4. Εμφάνιση διαθέσιμων αυτοκινήτων της εταιρείας με χρήση φίλτρων");
                System.out.println("5. Καταχώριση απόσυρσης ενός αυτοκινήτου του καταστήματος");
                System.out.println("Παρακαλώ πληκτρολογήστε έναν αριθμό από το 1 έως το 5 που αντιστοιχεί στην ενέργεια " +
                        "που επιθυμείτε να εκτελέσετε, ή πατήστε 0 για έξοδο: ");

                mainMenu = in.nextInt();
                in.nextLine();

                while (mainMenu > 5 || mainMenu < 0) {
                    System.out.println("Παρακαλώ διαβάστε ξανά τις οδηγίες και εισάγετε σωστό αριθμό: ");
                    try {
                        mainMenu = in.nextInt();
                        in.nextLine();
                    } catch (Exception e) {
                        in.nextLine();
                        mainMenu = -1;
                    }// try-catch
                }// while

                /*=====================================================================================================*/
                /*======= ΕΝΕΡΓΕΙΕΣ ΠΟΥ ΑΦΟΡΟΥΝ ΣΤΗΝ ΕΠΙΛΟΓΗ ΓΙΑ ΑΛΛΑΓΗ ΚΑΤΑΧΩΡΙΣΗΣ Ή ΕΙΣΑΓΩΓΗ ΝΕΑΣ ΚΑΤΑΧΩΡΙΣΗΣ =======*/
                /*=====================================================================================================*/

                if (mainMenu == 1) {
                    int subMenu = -1;
                    System.out.println("\nΜΕΝΟΥ ΑΛΛΑΓΗΣ Ή ΕΙΣΑΓΩΓΗΣ ΕΓΓΡΑΦΩΝ");
                    System.out.println("1. Εισαγωγή νέας εγγραφής που αφορά σε αυτοκίνητο");
                    System.out.println("2. Ενημέρωση παλιάς εγγραφής που αφορά σε αυτοκίνητο");
                    System.out.println("3. Εισαγωγή νέας εγγραφής που αφορά σε πελάτη");
                    System.out.println("4. Ενημέρωση παλιάς εγγραφής που αφορά σε πελάτη");
                    System.out.println("5. Εισαγωγή νέας εγγραφής που αφορά σε ενοικίαση");
                    System.out.println("6. Ενημέρωση παλιάς εγγραφής που αφορά σε ενοικίαση");
                    System.out.println("Παρακαλώ πληκτρολογήστε έναν αριθμό από το 1 έως το 6 που αντιστοιχεί στην ενέργεια " +
                            "που επιθυμείτε να εκτελέσετε, ή πατήστε 0 για επιστροφή στο αρχικό μενού: ");
                    try {
                        subMenu = in.nextInt();
                        in.nextLine();
                    } catch (Exception e) {
                        in.nextLine();
                        subMenu = -1;
                    }// try-catch
                    while (subMenu > 6 || subMenu < 0) {
                        System.out.println("Παρακαλώ διαβάστε ξανά τις οδηγίες και εισάγετε σωστό αριθμό: ");
                        try {
                            subMenu = in.nextInt();
                            in.nextLine();
                        } catch (Exception e) {
                            in.nextLine();
                            subMenu = -1;
                        }// try-catch
                    }// while

                    System.out.println();
                    if (subMenu == 1) {
                        Car car = new Car();
                        boolean allGood = false;
                        while (!allGood) {
                            try {

                                System.out.println("Εισάγετε αριθμό πινακίδας: ");
                                String licensePlate = in.nextLine();
                                car.setLicensePlate(licensePlate);

                                System.out.println("Εισάγετε μάρκα και όνομα μοντέλου: ");
                                String model = in.nextLine();
                                car.setModel(model);

                                FunctionUtils.showAllCarTypes();
                                System.out.println("Εισάγετε ID τύπου αυτοκινήτου: ");
                                int typeid = in.nextInt();
                                car.setTypeid(typeid);

                                FunctionUtils.showAllCarSizes();
                                System.out.println("Εισάγετε ID μεγέθους αυτοκινήτου: ");
                                int sizeid = in.nextInt();
                                car.setSizeid(sizeid);

                                System.out.println("Εισάγετε αριθμό θέσεων: ");
                                int seats = in.nextInt();
                                car.setSeats(seats);

                                System.out.println("Εισάγετε αριθμό θυρών: ");
                                int doors = in.nextInt();
                                car.setDoors(doors);

                                System.out.println("Εισάγετε τιμή ανά ημέρα ενοικίασης: ");
                                float pricePerDay = in.nextFloat();
                                car.setPricePerDay(pricePerDay);

                                in.nextLine();

                                if (typeid > 0 && typeid < 5 && sizeid > 0 && sizeid < 5 && seats > 0 && doors > 0 && pricePerDay > 0 && pricePerDay < 200)
                                    allGood = true;
                                else
                                    System.out.println("Κάτι πήγε στραβά με κάποια από τις τιμές που εισάγατε. Παρακαλώ προσπαθήστε ξανά.");

                                car.setStoreid(storeOption);
                                String conventional;
                                if (typeid == 1 || typeid == 2) {
                                    conventional = "Όχι";
                                    car.setConventional(conventional);
                                } else if (typeid == 3 || typeid == 4) {
                                    conventional = "Ναι";
                                    car.setConventional(conventional);
                                }// if - else if

                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Κάτι πήγε στραβά με κάποια από τις τιμές που εισάγατε. Παρακαλώ προσπαθήστε ξανά.");
                            }//try-catch
                        }//while
                        int count = CarQueries.insertNewCar(car);
                        if (count == 1) System.out.println("Η εγγραφή ήταν επιτυχής!");
                        else System.out.println("Η εγγραφή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 1


                    if (subMenu == 2) {
                        FunctionUtils.showFullCarInfoByStoreid(storeOption);
                        System.out.println("Εισάγετε το ID του αυτοκινήτου του οποίου τα στοιχεία θα θέλατε να τροποποιήσετε: ");
                        int carid = -1;
                        while (carid < 1) {
                            try {
                                carid = in.nextInt();
                                in.nextLine();
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ διαβάστε τις οδηγίες και προσπαθήστε ξανά: ");
                            }// try-catch
                        }// while
                        Car car = CarQueries.getCarByCarid(carid);
                        car = FunctionUtils.changeCarObject(car, in);
                        int count = CarQueries.updateCar(car);
                        if (count == 1) System.out.println("Η αλλαγή ήταν επιτυχής!");
                        else System.out.println("Η αλλαγή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 2


                    if (subMenu == 3) {
                        Customer customer = new Customer();
                        boolean allGood = false;
                        while (!allGood) {
                            try {
                                System.out.println("Εισάγετε αριθμό διπλώματος: ");
                                String driverLicense = in.nextLine();
                                customer.setDriverLicense(driverLicense);

                                System.out.println("Εισάγετε όνομα: ");
                                String firstName = in.nextLine();
                                customer.setFirstName(firstName);

                                System.out.println("Εισάγετε επώνυμο: ");
                                String lastName = in.nextLine();
                                customer.setLastName(lastName);

                                System.out.println("Εισάγετε email: ");
                                String email = in.nextLine();
                                customer.setEmail(email);

                                System.out.println("Εισάγετε κινητό: ");
                                String cellphoneNumber = in.nextLine();
                                customer.setCellphoneNumber(cellphoneNumber);

                                System.out.println("Εισάγετε τηλέφωνο κατοικίας: ");
                                String homephoneNumber = in.nextLine();
                                customer.setHomephoneNumber(homephoneNumber);

                                allGood = true;
                            } catch (Exception e) {
                                System.out.println("Κάτι πήγε στραβά. Παρακαλώ προσπαθήστε ξανά.");
                            }// try - catch
                        }// while
                        int count = CustomerQueries.insertNewCustomer(customer);
                        if (count == 1) System.out.println("Η εγγραφή ήταν επιτυχής!");
                        else System.out.println("Η εγγραφή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 3

                    if (subMenu == 4) {
                        FunctionUtils.showAllCustomers();
                        System.out.println("Εισάγετε τον αριθμό διπλώματος του πελάτη του οποίου τα στοιχεία θα θέλατε να τροποποιήσετε: ");
                        String driverLicense = in.nextLine();

                        Customer customer = CustomerQueries.getCustomerByDriverLicense(driverLicense);
                        customer = FunctionUtils.changeCustomerObject(customer, in);
                        int count = CustomerQueries.updateCustomer(customer, driverLicense);
                        if (count == 1) System.out.println("Η αλλαγή ήταν επιτυχής!");
                        else System.out.println("Η αλλαγή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 4

                    if (subMenu == 5) {
                        Rent rent = new Rent();
                        FunctionUtils.showAllCustomers();
                        System.out.println("Εισάγετε άδεια οδήγησης του πελάτη: ");
                        String driverLicense = in.nextLine();
                        rent.setCustomerDriverLicense(driverLicense);

                        System.out.println("Εισάγετε ID αυτοκινήτου προς ενοικίαση: ");
                        int carid = -1;
                        while (carid < 1) {
                            try {
                                carid = in.nextInt();
                                in.nextLine();
                                if (carid < 1) System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                            }// try - catch
                        }// while
                        rent.setCarid(carid);

                        FunctionUtils.showLocationByStoreid(storeOption);
                        int deliveryLocationid = -1;
                        int returnLocationid = -1;
                        while (deliveryLocationid < 1 || returnLocationid < 1) {
                            try {
                                System.out.println("Εισάγετε ID τοποθεσίας παραλαβής: ");
                                deliveryLocationid = in.nextInt();
                                System.out.println("Εισάγετε ID τοποθεσίας παράδοσης: ");
                                returnLocationid = in.nextInt();
                                in.nextLine();
                                if (deliveryLocationid < 1 || returnLocationid < 1)
                                    System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                            }// try - catch
                        }// while
                        rent.setDeliveryLocationid(deliveryLocationid);
                        rent.setReturnLocationid(returnLocationid);

                        int year, month, day, hour, minute;
                        year = month = day = hour = minute = -1;
                        System.out.println("Εισαγωγή στοιχείων που αφορούν στον χρόνο παραλαβής.");
                        while (year < 2024 || month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                            try {
                                System.out.println("Εισάγετε έτος (π.χ. 2024): ");
                                year = in.nextInt();
                                System.out.println("Εισάγετε μήνα (σε αριθμό): ");
                                month = in.nextInt();
                                System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): ");
                                day = in.nextInt();
                                System.out.println("Εισάγετε ώρα (0 έως 23): ");
                                hour = in.nextInt();
                                System.out.println("Εισάγετε λεπτά (0 έως 59): ");
                                minute = in.nextInt();
                                in.nextLine();
                                if (year < 2024 || month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59)
                                    System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                            }// try-catch
                        }// while
                        Timestamp deliveryDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
                        rent.setDeliveryDate(deliveryDate);

                        year = month = day = hour = minute = -1;
                        System.out.println("Εισαγωγή στοιχείων που αφορούν στον χρόνο παράδοσης.");
                        while (year < 2024 || month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                            try {
                                System.out.println("Εισάγετε έτος (π.χ. 2024): ");
                                year = in.nextInt();
                                System.out.println("Εισάγετε μήνα (σε αριθμό): ");
                                month = in.nextInt();
                                System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): ");
                                day = in.nextInt();
                                System.out.println("Εισάγετε ώρα (0 έως 23): ");
                                hour = in.nextInt();
                                System.out.println("Εισάγετε λεπτά (0 έως 59): ");
                                minute = in.nextInt();
                                in.nextLine();
                                if (year < 2024 || month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59)
                                    System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ προσπαθήστε ξανά.");
                            }// try-catch
                        }// while
                        Timestamp returnDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
                        rent.setReturnDate(returnDate);

                        int count = RentQueries.insertNewRent(rent);
                        if (count == 1) System.out.println("Η εγγραφή ήταν επιτυχής!");
                        else System.out.println("Η εγγραφή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 5

                    if (subMenu == 6) {
                        FunctionUtils.showFullRentInfoByStoreid(storeOption);
                        System.out.println("Εισάγετε το ID της ενοικίασης της οποίας τα στοιχεία θα θέλατε να τροποποιήσετε: ");
                        int rentid = -1;
                        while (rentid < 1) {
                            try {
                                rentid = in.nextInt();
                                in.nextLine();
                                if (rentid < 1) System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                            } catch (Exception e) {
                                in.nextLine();
                                System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                            }// try-catch
                        }// while

                        Rent rent = RentQueries.getRentByRentid(rentid);
                        rent = FunctionUtils.changeRentObject(rent, in, storeOption);
                        int count = RentQueries.updateRent(rent);
                        if (count == 1) System.out.println("Η αλλαγή ήταν επιτυχής!");
                        else System.out.println("Η αλλαγή δεν ήταν επιτυχής...");
                    }// if mainMenu is 1 AND subMenu is 6
                    System.out.println();
                }// if mainMenu is 1

                /*=====================================================================================================*/
                /*============== ΕΝΕΡΓΕΙΕΣ ΠΟΥ ΑΦΟΡΟΥΝ ΣΤΗΝ ΕΜΦΑΝΙΣΗ ΟΛΩΝ ΤΩΝ ΑΥΤΟΚΙΝΗΤΩΝ ΤΗΣ ΕΤΑΙΡΕΙΑΣ  ==============*/
                /*=====================================================================================================*/

                if (mainMenu == 2) {
                    System.out.println();
                    FunctionUtils.showAllFullCarInfo();
                    System.out.println();
                }// if mainMenu is 2

                /*=====================================================================================================*/
                /*======== ΕΝΕΡΓΕΙΕΣ ΠΟΥ ΑΦΟΡΟΥΝ ΣΤΗΝ ΕΜΦΑΝΙΣΗ ΤΩΝ ΜΗ ΔΙΑΘΕΣΙΜΩΝ ΑΥΤΟΚΙΝΗΤΩΝ ΤΟΥ ΚΑΤΑΣΤΗΜΑΤΟΣ =========*/
                /*=====================================================================================================*/

                if (mainMenu == 3) {
                    System.out.println();
                    FunctionUtils.showNotAvailableCarInfoByStoreid(storeOption);
                    System.out.println();
                }// if mainMenu is 3

                /*=====================================================================================================*/
                /*===== ΕΝΕΡΓΕΙΕΣ ΠΟΥ ΑΦΟΡΟΥΝ ΣΤΗΝ ΕΜΦΑΝΙΣΗ ΔΙΑΘΕΣΙΜΩΝ ΑΥΤΟΚΙΝΗΤΩΝ ΤΗΣ ΕΤΑΙΡΕΙΑΣ ΜΕ ΧΡΗΣΗ ΦΙΛΤΡΩΝ =====*/
                /*=====================================================================================================*/

                if (mainMenu == 4) {
                    int cityOption, deliveryLocationid, returnLocationid, storeid;
                    int year = -1, month = -1, day = -1, hour = -1, minute = -1;
                    Timestamp deliveryDate, returnDate;
                    int carTypeid = -1;
                    String city;
                    System.out.println();
                    System.out.println("Πρέπει να επιλέξετε τιμές για τα φίλτρα που θα χρησιμοποιηθούν στην αναζήτηση");
                    System.out.println("Για ποια πόλη σας ενδιαφέρει η ενοικίαση; Πατήστε 1 για Αθήνα ή 2 για Θεσσαλονίκη: ");
                    try {
                        cityOption = in.nextInt();
                    } catch (Exception e) {
                        in.nextLine();
                        cityOption = 0;
                    }// try-catch
                    while (cityOption != 1 && cityOption != 2) {
                        System.out.println("Παρακαλώ διαβάστε τις οδηγίες και πληκτρολογήστε ξανά: ");
                        try {
                            cityOption = in.nextInt();
                        } catch (Exception e) {
                            in.nextLine();
                            cityOption = 0;
                        }// try-catch
                    }// while
                    if (cityOption == 1) city = "Αθήνα";
                    else city = "Θεσσαλονίκη";
                    System.out.println("Από ποιο σημείο εξυπηρέτησης σας ενδιαφέρει να γίνει η παραλαβή του αυτοκινήτου;");
                    FunctionUtils.showLocationByCity(city);
                    System.out.println("Παρακαλώ εισάγετε το ID ενός από τα παραπάνω σημεία εξυπηρέτησης: ");
                    try {
                        deliveryLocationid = in.nextInt();
                    } catch (Exception e) {
                        in.nextLine();
                        deliveryLocationid = -1;
                    }// try-catch
                    while (deliveryLocationid < 0) {
                        try {
                            System.out.println("Παρακαλώ διαβάστε τις οδηγίες και πληκτρολογήστε ξανά: ");
                            deliveryLocationid = in.nextInt();
                        } catch (Exception e) {
                            in.nextLine();
                            deliveryLocationid = -1;
                        }// try-catch
                    }// while
                    storeid = StoreQueries.getStoreidFromLocationid(deliveryLocationid);
                    System.out.println("Το σημείο εξυπηρέτησης που επιλέξατε αντιστοιχεί στο κατάστημα με ID " + storeid);
                    System.out.println();
                    System.out.println("Σε ποιο σημείο εξυπηρέτησης σας ενδιαφέρει να γίνει η παράδοση του αυτοκινήτου;");
                    System.out.println("Εισάγετε το ίδιο ID εάν θέλετε το σημείο παράδοσης να είναι ίδιο με το σημείο παραλαβής");
                    FunctionUtils.showLocationByStoreid(storeid);
                    System.out.println("Παρακαλώ εισάγετε το ID ενός από τα παραπάνω σημεία εξυπηρέτησης: ");
                    try {
                        returnLocationid = in.nextInt();
                    } catch (Exception e) {
                        in.nextLine();
                        returnLocationid = -1;
                    }// try-catch
                    while (returnLocationid < 0) {
                        try {
                            System.out.println("Παρακαλώ διαβάστε τις οδηγίες και πληκτρολογήστε ξανά: ");
                            returnLocationid = in.nextInt();
                        } catch (Exception e) {
                            in.nextLine();
                            returnLocationid = -1;
                        }// try-catch
                    }// while
                    System.out.println("Πρέπει να εισάγετε ημερομηνία και ώρα παραλαβής που επιθυμείτε");
                    boolean notGood = true;
                    while (notGood) {
                        try {
                            System.out.println("Εισάγετε έτος: ");
                            year = in.nextInt();
                            System.out.println("Εισάγετε μήνα (σε αριθμό): ");
                            month = in.nextInt();
                            System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): ");
                            day = in.nextInt();
                            System.out.println("Εισάγετε ώρα (0 έως 23): ");
                            hour = in.nextInt();
                            System.out.println("Εισάγετε λεπτά (0 έως 59): ");
                            minute = in.nextInt();
                            if (year > 2023 && year < 3000 && month > 0 && month < 13 && day > 0 && day < 32 && hour > -1 && hour < 24 && minute > -1 && minute < 60)
                                notGood = false;
                            else
                                System.out.println("Κάτι πήγε στραβά με τις τιμές που εισάγατε. Παρακαλώ δοκιμάστε ξανά " +
                                        "προσέχοντας τις τιμές που πληκτρολογείτε");
                        } catch (Exception e) {
                            in.nextLine();
                            System.out.println("Κάτι πήγε στραβά με τις τιμές που εισάγατε. Παρακαλώ δοκιμάστε ξανά " +
                                    "προσέχοντας τις τιμές που πληκτρολογείτε");
                        }// try-catch
                    }// while
                    deliveryDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
                    System.out.println("Πρέπει να εισάγετε ημερομηνία και ώρα παράδοσης που επιθυμείτε");
                    notGood = true;
                    while (notGood) {
                        try {
                            System.out.println("Εισάγετε έτος: ");
                            year = in.nextInt();
                            System.out.println("Εισάγετε μήνα (σε αριθμό): ");
                            month = in.nextInt();
                            System.out.println("Εισάγετε ημέρα του μήνα(σε αριθμό): ");
                            day = in.nextInt();
                            System.out.println("Εισάγετε ώρα (0 έως 23): ");
                            hour = in.nextInt();
                            System.out.println("Εισάγετε λεπτά (0 έως 59): ");
                            minute = in.nextInt();
                            if (year > 2023 && month > 0 && month < 13 && day > 0 && day < 32 && hour > -1 && hour < 24 && minute > -1 && minute < 60)
                                notGood = false;
                            else
                                System.out.println("Κάτι πήγε στραβά με τις τιμές που εισάγατε. Παρακαλώ δοκιμάστε ξανά " +
                                        "προσέχοντας τις τιμές που πληκτρολογείτε");
                            in.nextLine();
                        } catch (Exception e) {
                            in.nextLine();
                            System.out.println("Κάτι πήγε στραβά με τις τιμές που εισάγατε. Παρακαλώ δοκιμάστε ξανά " +
                                    "προσέχοντας τις τιμές που πληκτρολογείτε");
                        }// try-catch
                    }// while
                    returnDate = FunctionUtils.integersToTimestamp(year, month, day, hour, minute);
                    System.out.println("Μπορείτε να περιορίσετε την αναζήτηση, εισάγοντας και τύπο αυτοκινήτου");
                    FunctionUtils.showAllCarTypes();
                    System.out.println("Εαν επιθυμείτε να εισάγετε και τύπο αυτοκινήτου, πληκτρολογήστε το ID του τύπου. " +
                            "Αλλιώς πληκτρολογήστε '0': ");
                    notGood = true;
                    while (notGood) {
                        try {
                            carTypeid = in.nextInt();
                            if (carTypeid > -1 && carTypeid < 5) notGood = false;
                            else
                                System.out.println("Παρακαλώ δοκιμάστε ξανά προσέχοντας την τιμή που πληκτρολογείτε: ");
                        } catch (Exception e) {
                            in.nextLine();
                            System.out.println("Παρακαλώ δοκιμάστε ξανά προσέχοντας την τιμή που πληκτρολογείτε: ");
                        }// try-catch
                    }// while
                    System.out.println();
                    if (carTypeid == 0) {
                        FunctionUtils.showFilteredAvailableCars(deliveryLocationid, deliveryDate, returnDate);
                    } else {
                        FunctionUtils.showFilteredAvailableCarsByCarTypeid(deliveryLocationid, deliveryDate, returnDate, carTypeid);
                    }// if-else
                    System.out.println();
                }// if mainMenu is 4

                /*=====================================================================================================*/
                /*=============== ΕΝΕΡΓΕΙΕΣ ΠΟΥ ΑΦΟΡΟΥΝ ΣΤΗΝ ΑΠΟΣΥΡΣΗ ΕΝΟΣ ΑΥΤΟΚΙΝΗΤΟΥ ΤΟΥ ΚΑΤΑΣΤΗΜΑΤΟΣ ===============*/
                /*=====================================================================================================*/

                if (mainMenu == 5) {
                    int carToWithdraw, count;
                    Car car;
                    System.out.println();
                    FunctionUtils.showIdleCarInfoByStoreid(storeOption);
                    System.out.println();
                    System.out.println("Εισάγετε το ID του αυτοκινήτου προς απόσυρση: ");
                    try {
                        carToWithdraw = in.nextInt();
                        in.nextLine();
                    } catch (Exception e) {
                        in.nextLine();
                        carToWithdraw = -1;
                    }// try-catch
                    car = CarQueries.getCarByCarid(carToWithdraw);
                    if (car == null) System.out.println("Δεν υπάρχει αυτοκίνητο με αυτό το ID!");
                    else {
                        count = CarQueries.deleteCar(carToWithdraw);
                        if (count == 1) System.out.println("H διαγραφή ήταν επιτυχής!");
                        else System.out.println("Η διαγραφή δεν ήταν επιτυχής! ");
                    }// if-else
                    System.out.println();
                }// if mainMenu is 5
            }// while (MAIN MENU)
            in.close();
        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }catch (SQLException s){
            s.printStackTrace();
        }catch (java.text.ParseException p){
            p.printStackTrace();
        }// try-catch
    }// main
}// class