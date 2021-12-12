package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.House;
import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HouseUseCase {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    public List<House> findAllHouses() {
        List<House> houseList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("SELECT h.housenumber, h.street, h.hNumber, h.postcode, p.city FROM ss2101_House AS h JOIN ss2101_DimPLZ as p ON h.postcode = p.postcode").executeQuery();
            while(resultSet.next()) {
                House house = new House();
                house.houseNumber = resultSet.getInt("HouseNumber");
                house.street = resultSet.getString("street");
                house.hNumber = resultSet.getInt("hNumber");
                house.postcode = resultSet.getInt("postcode");
                house.city = resultSet.getString("city");
                houseList.add(house);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return houseList;
    }

    public List<House> findHouseById(Integer id) {
        List<House> houseList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT h.housenumber, h.street, h.hNumber, h.postcode, p.city FROM ss2101_House AS h JOIN ss2101_DimPLZ as p ON h.postcode = p.postcode WHERE h.housenumber = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                House house = new House();
                house.houseNumber = resultSet.getInt("HouseNumber");
                house.street = resultSet.getString("street");
                house.hNumber = resultSet.getInt("hNumber");
                house.postcode = resultSet.getInt("postcode");
                house.city = resultSet.getString("city");
                houseList.add(house);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return houseList;
    }

    public String createHouse(House house) {
        Connection connection = serverConnection.getConnection();
        String query = "INSERT INTO ss2101_House (street, hNumber, postcode) VALUES (?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, house.street);
            pstmt.setString(2, house.hNumber.toString());
            pstmt.setString(3, house.postcode.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "created house in " + house.street + " " + house.hNumber + ", " + house.postcode + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "create house in " + house.street + " " + house.hNumber + ", " + house.postcode + " failed";
        }
    }

    public String updateHouse(House house) {
        Connection connection = serverConnection.getConnection();
        String query = "UPDATE ss2101_House SET street = ?, hNumber = ?, postcode = ? WHERE housenumber = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, house.street);
            pstmt.setString(2, house.hNumber.toString());
            pstmt.setString(3, house.postcode.toString());
            pstmt.setString(4, house.houseNumber.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "updated house in " + house.street + " " + house.hNumber + ", " + house.postcode + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "update house in " + house.street + " " + house.hNumber + ", " + house.postcode + " failed";
        }
    }

    public String deleteHouseById(Integer id) {
        Connection connection = serverConnection.getConnection();
        String query = "DELETE FROM ss2101_House WHERE housenumber = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "deleted house successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "delete house failed";
        }
    }
}
