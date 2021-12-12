package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.Renter;
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
public class RenterUseCase {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    public List<Renter> findAllRenters() {
        List<Renter> renterList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM ss2101_Renter").executeQuery();
            while (resultSet.next()) {
                Renter renter = new Renter();
                renter.renterId = Integer.parseInt(resultSet.getString("RenterID"));
                renter.rFirstname = resultSet.getString("rFirstname");
                renter.rLastname = resultSet.getString("rLastname");
                renter.rGender = resultSet.getString("rGender").charAt(0);

                renterList.add(renter);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return renterList;
    }

    public List<Renter> findRenterById(Integer id) {
        List<Renter> renterList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT * FROM ss2101_Renter WHERE RenterID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Renter renter = new Renter();
                renter.renterId = Integer.parseInt(resultSet.getString("RenterID"));
                renter.rFirstname = resultSet.getString("rFirstname");
                renter.rLastname = resultSet.getString("rLastname");
                renter.rGender = resultSet.getString("rGender").charAt(0);

                renterList.add(renter);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return renterList;
    }

    public String createRenter(Renter renter) {
        Connection connection = serverConnection.getConnection();
        String query = "INSERT INTO ss2101_Renter (rFirstname, rLastname, rGender) VALUES (?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, renter.rFirstname);
            pstmt.setString(2, renter.rLastname);
            pstmt.setString(3, renter.rGender.toString().toUpperCase());
            pstmt.executeUpdate();
            pstmt.close();
            return "created " + renter.rFirstname + " " + renter.rLastname + " sucesfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "create " + renter.rFirstname + " " + renter.rLastname + " failed";
        }
    }

    public String updateRenter(Renter renter) {
        Connection connection = serverConnection.getConnection();
        String query = "UPDATE ss2101_Renter SET rFirstname = ?, rLastname = ?, rGender = ? WHERE RenterID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, renter.rFirstname);
            pstmt.setString(2, renter.rLastname);
            pstmt.setString(3, renter.rGender.toString().toUpperCase());
            pstmt.setString(4, renter.renterId.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "updated " + renter.rFirstname + " " + renter.rLastname + " sucesfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "update " + renter.rFirstname + " " + renter.rLastname + " failed";
        }
    }

    public String deleteRenter(Integer id) {
        Connection connection = serverConnection.getConnection();
        String query = "DELETE FROM ss2101_Renter WHERE RenterID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "deleted renter " + id + " sucesfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "delete renter " + id + " failed";
        }
    }
}
