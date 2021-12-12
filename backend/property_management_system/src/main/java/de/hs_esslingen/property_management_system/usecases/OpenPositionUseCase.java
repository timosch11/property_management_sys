package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.OpenPosition;
import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenPositionUseCase {

    @Autowired
    JdbcSQLServerConnection serverConnection;

    public List<OpenPosition> getAllOpenPositions() {
        List<OpenPosition> openPositionList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        try {
            ResultSet resultSet = connection.prepareCall("{call ss2101_calculateOpenPositionByRenterId}").executeQuery();
            while (resultSet.next()) {
                OpenPosition openPosition = new OpenPosition();
                openPosition.renterId = resultSet.getInt("RenterID");
                openPosition.rFirstname = resultSet.getString("rFirstName");
                openPosition.rLastname = resultSet.getString("rlastName");
                openPosition.balance = resultSet.getDouble("Bilanz");
                openPositionList.add(openPosition);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return openPositionList;
    }
}
