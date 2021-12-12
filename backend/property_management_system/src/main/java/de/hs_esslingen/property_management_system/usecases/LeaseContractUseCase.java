package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.LeaseContract;
import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaseContractUseCase {

    @Autowired
    JdbcSQLServerConnection serverConnection;

    public List<LeaseContract> findAllLeaseContracts() {
        List<LeaseContract> leaseContractList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM ss2101_LeaseContract").executeQuery();
            while (resultSet.next()) {
                LeaseContract leaseContract = new LeaseContract();
                leaseContract.contractId = Integer.valueOf(resultSet.getString("ContractID"));
                leaseContract.leaseAmount = Double.valueOf(resultSet.getString("LeaseAmount"));
                leaseContract.dateOfSignature = LocalDate.parse(resultSet.getString("DateOfSignature"));
                leaseContract.additionalCosts = Double.valueOf(resultSet.getString("AdditionalCosts"));
                leaseContract.numberOfPersons = Integer.valueOf(resultSet.getString("NumberOfPersons"));
                leaseContract.renterId = Integer.valueOf(resultSet.getString("RenterID"));
                leaseContract.apartmentId = Integer.valueOf(resultSet.getString("AppartmentID"));
                leaseContract.active = resultSet.getString("Active").toCharArray()[0];
                leaseContractList.add(leaseContract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return leaseContractList;
    }

    public List<LeaseContract> findLeaseContractsById(Integer id) {
        List<LeaseContract> leaseContractList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT * FROM ss2101_LeaseContract WHERE ContractID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                LeaseContract leaseContract = new LeaseContract();
                leaseContract.contractId = Integer.valueOf(resultSet.getString("ContractID"));
                leaseContract.leaseAmount = Double.valueOf(resultSet.getString("LeaseAmount"));
                leaseContract.dateOfSignature = LocalDate.parse(resultSet.getString("DateOfSignature"));
                leaseContract.additionalCosts = Double.valueOf(resultSet.getString("AdditionalCosts"));
                leaseContract.numberOfPersons = Integer.valueOf(resultSet.getString("NumberOfPersons"));
                leaseContract.renterId = Integer.valueOf(resultSet.getString("RenterID"));
                leaseContract.apartmentId = Integer.valueOf(resultSet.getString("AppartmentID"));
                leaseContract.active = resultSet.getString("Active").toCharArray()[0];
                leaseContractList.add(leaseContract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return leaseContractList;
    }

    public String createLeaseContract(LeaseContract leaseContract) {
        Connection connection = serverConnection.getConnection();
        String query = "INSERT INTO ss2101_LeaseContract (LeaseAmount, DateOfSignature, AdditionalCosts, NumberOfPersons, RenterID, AppartmentID, Active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, leaseContract.leaseAmount.toString());
            pstmt.setString(2, leaseContract.dateOfSignature.toString());
            pstmt.setString(3, leaseContract.additionalCosts.toString());
            pstmt.setString(4, leaseContract.numberOfPersons.toString());
            pstmt.setString(5, leaseContract.renterId.toString());
            pstmt.setString(6, leaseContract.apartmentId.toString());
            pstmt.setString(7, leaseContract.active.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "created lease contract for renter " + leaseContract.renterId + " in apartment " + leaseContract.apartmentId + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "create lease contract for renter " + leaseContract.renterId + " in apartment " + leaseContract.apartmentId + " failed";
        }
    }

    public String updateLeaseContract(LeaseContract leaseContract) {
        Connection connection = serverConnection.getConnection();
        String query = "UPDATE ss2101_LeaseContract SET LeaseAmount = ?, DateOfSignature = ?, AdditionalCosts = ?, NumberOfPersons = ?, RenterID = ?, AppartmentID = ?, Active= ? WHERE ContractID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, leaseContract.leaseAmount.toString());
            pstmt.setString(2, leaseContract.dateOfSignature.toString());
            pstmt.setString(3, leaseContract.additionalCosts.toString());
            pstmt.setString(4, leaseContract.numberOfPersons.toString());
            pstmt.setString(5, leaseContract.renterId.toString());
            pstmt.setString(6, leaseContract.apartmentId.toString());
            pstmt.setString(7, leaseContract.active.toString());
            pstmt.setString(8, leaseContract.contractId.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "updated lease contract for renter " + leaseContract.renterId + " in apartment " + leaseContract.apartmentId + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "update lease contract for renter " + leaseContract.renterId + " in apartment " + leaseContract.apartmentId + " failed";
        }
    }

    public String deleteLeaseContract(Integer id) {
        Connection connection = serverConnection.getConnection();
        String query = "DELETE FROM ss2101_LeaseContract WHERE ContractID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
            pstmt.close();
            return "deleted lease contract " + id + " successfully";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "delete lease contract " + id + " failed";
        }
    }
}
