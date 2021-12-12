package de.hs_esslingen.property_management_system.interfaceadapters.db;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class JdbcSQLServerConnection {
    private final String DB_URL = "jdbc:sqlserver://itnt0005:1433;databaseName=Infosys;user=wkb6;password=wkb6";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
