package de.hs_esslingen.property_management_system.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

public class StatementOfBankAccount {

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByName(column = "Buchungstag")
    public LocalDate postingDate;
    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByName(column = "Valutadatum")
    public LocalDate valueDate;
    @CsvBindByName(column = "Buchungstext")
    public String postingText;
    @CsvBindByName(column = "Verwendungszweck")
    public String paymentReason;
    @CsvBindByName(column = "Begünstigter/Zahlungspflichtiger")
    public String debtorCreditor;
    @CsvBindByName(column = "Betrag")
    public String amountOfPayment;

    public StatementOfBankAccount() {
    }

    public String getAmountOfPaymentForSql() {
        return amountOfPayment.replace(".", "").replace(",", ".");
    }
}
