package de.hs_esslingen.property_management_system.entities;

import java.time.LocalDate;

public class Transaction {

    public LocalDate postingDate;
    public String paymentReason;
    public Double transactionAmount;

    public Transaction() {
    }
}
