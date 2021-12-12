/**
 * This class is used to execute repeatedly methods,
 * the stored procedure can't be executed by the DBMS directly, because the user doesn't has the privileges to use the SQL Server Agent
 * */

package de.hs_esslingen.property_management_system.utils;

import de.hs_esslingen.property_management_system.interfaceadapters.db.JdbcSQLServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class JobScheduler {

    @Autowired
    private JdbcSQLServerConnection serverConnection;

    @Scheduled(cron = "0 0 0 1 * *")
    private void rentToTransactionMonthly() {
        Connection connection = serverConnection.getConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall("{call ss2101_rentToTransactionMonthly}");
            callableStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
