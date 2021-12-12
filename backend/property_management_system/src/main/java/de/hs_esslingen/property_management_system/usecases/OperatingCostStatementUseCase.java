package de.hs_esslingen.property_management_system.usecases;

import de.hs_esslingen.property_management_system.entities.OperatingCostStatement;
import de.hs_esslingen.property_management_system.entities.OperatingCostStatementRenter;
import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import de.hs_esslingen.property_management_system.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperatingCostStatementUseCase {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    public List<OperatingCostStatementRenter> getAllRentersWithTransactions() {
        List<OperatingCostStatementRenter> operatingCostStatementRenterList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT rv.RenterID, r.rFirstName, r.rLastName, rv.[year], (SELECT TOP 1 active FROM ss2101_LeaseContract AS l WHERE l.RenterID = rv.RenterID ORDER BY active ASC) AS active FROM ss2101_RenterView AS rv JOIN ss2101_renter AS r ON r.RenterID = rv.RenterID";
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                OperatingCostStatementRenter operatingCostStatementRenter = new OperatingCostStatementRenter();
                operatingCostStatementRenter.renterId = resultSet.getInt("RenterID");
                operatingCostStatementRenter.rFirstName = resultSet.getString("rFirstName");
                operatingCostStatementRenter.rLastName = resultSet.getString("rLastName");
                operatingCostStatementRenter.year = Year.parse(resultSet.getString("year"));
                operatingCostStatementRenter.activeRenter = resultSet.getString("Active").toCharArray()[0];
                operatingCostStatementRenterList.add(operatingCostStatementRenter);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return operatingCostStatementRenterList;
    }

    public Workbook getOperatingCostStatementExcelForRenterByIdAndYear(Integer renterId, Year year) {
        List<OperatingCostStatement> operatingCostStatementList = getOperatingCostStatementByRenterIdAndYear(renterId, year);
        String[] headerValues = getHeaderValues(renterId);

        Workbook workbook = null;
        try {
            workbook = ExcelUtil.loadExcelSheetFromTemplate("operating cost statement template.xlsx");
            workbook = ExcelUtil.setAddressHeader(workbook, headerValues);
            workbook = ExcelUtil.setOperatingCostToExcel(workbook, operatingCostStatementList);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                workbook.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return workbook;
    }

    private String[] getHeaderValues(Integer renterId) {
        int headerValuesAmount = 6;
        String[] headerValues = new String[headerValuesAmount];
        Connection connection = serverConnection.getConnection();
        String query = "SELECT DISTINCT r.rFirstname, r.rLastName, h.street, h.hNumber, h.Postcode, p.city FROM ss2101_Renter AS r JOIN ss2101_LeaseContract AS l ON r.RenterID = l.RenterID JOIN ss2101_Appartment AS a ON a.AppartmentID = l.AppartmentID JOIN ss2101_House AS h ON h.Housenumber = a.Housenumber JOIN ss2101_DimPLZ AS p ON h.Postcode = p.Postcode WHERE r.RenterID = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, renterId.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                for (int i = 0; i < headerValuesAmount; i++) {
                    headerValues[i] = resultSet.getString(i+1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return headerValues;
    }

    private List<OperatingCostStatement> getOperatingCostStatementByRenterIdAndYear(Integer renterId, Year year) {
        List<OperatingCostStatement> operatingCostStatementList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();
        String query = "SELECT  [Payment Reason], TotalAmount, Aufgeteilt FROM ss2101_RenterView WHERE RenterID = ? AND [Year] = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, renterId.toString());
            pstmt.setString(2, year.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                OperatingCostStatement operatingCostStatement = new OperatingCostStatement();
                operatingCostStatement.paymentReason = resultSet.getString("Payment Reason");
                operatingCostStatement.totalAmount = resultSet.getDouble("TotalAmount");
                operatingCostStatement.splittedAmount = resultSet.getDouble("Aufgeteilt");
                operatingCostStatementList.add(operatingCostStatement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return operatingCostStatementList;
    }

}
