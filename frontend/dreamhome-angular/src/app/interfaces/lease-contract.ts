import { MonoTypeOperatorFunction } from "rxjs";

export interface LeaseContract {
    contractId: Number;
    leaseAmount: Number;
    dateOfSignature: String;
    additionalCosts: Number;
    numberOfPersons: Number;
    renterId: Number;
    apartmentId: Number;
    active: String;
}