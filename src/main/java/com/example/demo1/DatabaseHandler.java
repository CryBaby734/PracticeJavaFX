package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {

    public Connection getConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE +
                "(" + Const.USER_NAME + "," + Const.USER_LAST_NAME + ","
                + Const.USER_PASSWORD + "," + Const.USER_CONTACT + ")" +
                "VALUES(?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement prSt = con.prepareStatement(insert)) {
            prSt.setString(1, user.getFirstname());
            prSt.setString(2, user.getLastname());
            prSt.setString(3, user.getPassword());
            prSt.setString(4, user.getContactInfo());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
