package de.hs_esslingen.property_management_system.usecases;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.hs_esslingen.property_management_system.entities.StatementOfBankAccount;
import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatementOfBankAccountUseCase {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    public List<StatementOfBankAccount> findAllStatementsOfBankAccount() {
        List<StatementOfBankAccount> statementOfBankAccountList = new ArrayList<>();
        Connection connection = serverConnection.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM ss2101_RawData").executeQuery();
            while (resultSet.next()) {
                StatementOfBankAccount statementOfBankAccount = new StatementOfBankAccount();
                statementOfBankAccount.postingDate = LocalDate.parse(resultSet.getString("PostingDate"));
                statementOfBankAccount.valueDate = LocalDate.parse(resultSet.getString("ValueDate"));
                statementOfBankAccount.postingText = resultSet.getString("PostingText");
                statementOfBankAccount.paymentReason = resultSet.getString("PaymentReason");
                statementOfBankAccount.debtorCreditor = resultSet.getString("DebitorCreditorID");
                statementOfBankAccount.amountOfPayment = resultSet.getString("Amount");

                statementOfBankAccountList.add(statementOfBankAccount);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return statementOfBankAccountList;
    }

    public String insertStatementOfBankAccountFromCSV (MultipartFile multipartFile) {
        Reader reader;

        try {
            reader = new InputStreamReader(multipartFile.getInputStream());
        } catch (IOException e) {
            return "upload error";
        }
        CsvToBean csvReader = new CsvToBeanBuilder(reader)
                .withType(StatementOfBankAccount.class)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();

        List<StatementOfBankAccount> statementOfBankAccountList = csvReader.parse();
        Connection connection = serverConnection.getConnection();
        String query = "INSERT INTO ss2101_RawData (PostingDate, ValueDate, PostingText, PaymentReason, DebitorCreditorID, Amount) VALUES (?, ?, ?, ?, ?, ?)";

        int insertedRows = 0;
        for (int i = 0; i < statementOfBankAccountList.size(); i++) {
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, statementOfBankAccountList.get(i).postingDate.toString());
                pstmt.setString(2, statementOfBankAccountList.get(i).valueDate.toString());
                pstmt.setString(3, statementOfBankAccountList.get(i).postingText);
                pstmt.setString(4, statementOfBankAccountList.get(i).paymentReason);
                pstmt.setString(5, statementOfBankAccountList.get(i).debtorCreditor);
                pstmt.setString(6, statementOfBankAccountList.get(i).getAmountOfPaymentForSql());
                pstmt.executeUpdate();
                pstmt.close();
                insertedRows++;
            } catch (SQLException e) {
                e.printStackTrace();
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                return "upload error";
            }
        }
        return "uploaded " + insertedRows + " rows of " + statementOfBankAccountList.size();
    }
}
