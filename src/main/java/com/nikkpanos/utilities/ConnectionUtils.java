package com.nikkpanos.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionUtils {

    private ConnectionUtils(){}// This class is not meant to generate objects

    private static String url = "jdbc:mysql://localhost:3306/rental_cars?characterEncoding=utf-8";
    private static String userName = "root";
    private static String password = "3216549870";

    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        return con;
    }// getConnection

}// class
