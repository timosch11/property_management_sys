package de.hs_esslingen.property_management_system.entities;

import java.time.LocalDate;

public class LeaseContract {

    public Integer contractId;
    public Double leaseAmount;
    public LocalDate dateOfSignature;
    public Double additionalCosts;
    public Integer numberOfPersons;
    public Integer renterId;
    public Integer apartmentId;
    public Character active;

    public LeaseContract() {
    }
    
}
