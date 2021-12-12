package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.Apartment;
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
public class ApartmentUseCase {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    public List<Apartment> findAllApartments() {
        List<Apartment> apartmentList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("SELECT a.AppartmentID, a.MaxRenter, a.Size, a.AppartmentNumber, a.NumberRooms, a.SecurityDeposit, a.housenumber, h.street, h.postcode, p.city FROM ss2101_Appartment AS a JOIN ss2101_House AS h ON a.housenumber = h.housenumber JOIN ss2101_DimPLZ as p ON h.postcode = p.postcode").executeQuery();
            while(resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.apartmentId = Integer.parseInt(resultSet.getString("AppartmentID"));
                apartment.maxRenter = Integer.parseInt(resultSet.getString("MaxRenter"));
                apartment.size = Double.parseDouble(resultSet.getString("Size"));
                apartment.apartmentNumber = Integer.parseInt(resultSet.getString("AppartmentNumber"));
                apartment.numberRooms = Integer.parseInt(resultSet.getString("NumberRooms"));
                apartment.securityDeposit = Double.parseDouble(resultSet.getString("securityDeposit"));
                apartment.houseNumber = Integer.parseInt(resultSet.getString("HouseNumber"));
                apartment.street = resultSet.getString("street");
                apartment.postcode = Integer.parseInt(resultSet.getString("postcode"));
                apartment.city = resultSet.getString("city");

                apartmentList.add(apartment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return apartmentList;
    }

    public List<Apartment> findApartmentById(Integer id) {
        List<Apartment> apartmentList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT a.AppartmentID, a.MaxRenter, a.Size, a.AppartmentNumber, a.NumberRooms, a.SecurityDeposit, a.housenumber, h.street, h.postcode, p.city FROM ss2101_Appartment AS a JOIN ss2101_House AS h ON a.housenumber = h.housenumber JOIN ss2101_DimPLZ as p ON h.postcode = p.postcode WHERE a.AppartmentID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.apartmentId = Integer.parseInt(resultSet.getString("AppartmentID"));
                apartment.maxRenter = Integer.parseInt(resultSet.getString("MaxRenter"));
                apartment.size = Double.parseDouble(resultSet.getString("Size"));
                apartment.apartmentNumber = Integer.parseInt(resultSet.getString("AppartmentNumber"));
                apartment.numberRooms = Integer.parseInt(resultSet.getString("NumberRooms"));
                apartment.securityDeposit = Double.parseDouble(resultSet.getString("securityDeposit"));
                apartment.houseNumber = Integer.parseInt(resultSet.getString("HouseNumber"));
                apartment.street = resultSet.getString("street");
                apartment.postcode = Integer.parseInt(resultSet.getString("postcode"));
                apartment.city = resultSet.getString("city");

                apartmentList.add(apartment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return apartmentList;
    }

    public String createApartment(Apartment apartment) {
        Connection connection = serverConnection.getConnection();
        String query = "INSERT INTO ss2101_Appartment (MaxRenter, Size, AppartmentNumber, NumberRooms, SecurityDeposit, Housenumber) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, apartment.maxRenter.toString());
            pstmt.setString(2, apartment.size.toString());
            pstmt.setString(3, apartment.apartmentNumber.toString());
            pstmt.setString(4, apartment.numberRooms.toString());
            pstmt.setString(5, apartment.securityDeposit.toString());
            pstmt.setString(6, apartment.houseNumber.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "created apartment " + apartment.apartmentNumber + " in house number " + apartment.houseNumber + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "create apartment " + apartment.apartmentNumber + " in house number " + apartment.houseNumber + " failed";
        }
    }

    public String updateApartment(Apartment apartment) {
        Connection connection = serverConnection.getConnection();
        String query = "UPDATE ss2101_Appartment SET MaxRenter = ?, Size = ?, AppartmentNumber = ?, NumberRooms = ?, SecurityDeposit = ?, HouseNumber = ? WHERE AppartmentID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, apartment.maxRenter.toString());
            pstmt.setString(2, apartment.size.toString());
            pstmt.setString(3, apartment.apartmentNumber.toString());
            pstmt.setString(4, apartment.numberRooms.toString());
            pstmt.setString(5, apartment.securityDeposit.toString());
            pstmt.setString(6, apartment.houseNumber.toString());
            pstmt.setString(7, apartment.apartmentId.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "updated apartment " + apartment.apartmentNumber + " in house number " + apartment.houseNumber + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "update apartment " + apartment.apartmentNumber + " in house number " + apartment.houseNumber + " failed";
        }
    }

    public String deleteApartment(Integer id) {
        Connection connection = serverConnection.getConnection();
        String query = "DELETE FROM ss2101_Appartment WHERE AppartmentID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "deleted apartment " + id + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "delete apartment " + id+ " failed";
        }
    }
}
