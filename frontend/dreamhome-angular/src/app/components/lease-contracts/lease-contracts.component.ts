import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { LeaseContract } from 'src/app/interfaces/lease-contract';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-lease-contracts',
  templateUrl: './lease-contracts.component.html',
  styleUrls: ['./lease-contracts.component.css']
})
export class LeaseContractsComponent implements OnInit {
  leaseContracts: LeaseContract[] = [];
  rawLeaseContracts: LeaseContract[] = [];
  contractIdAsc: boolean = true;
  leaseAmountAsc: boolean = true;
  dateOfSignatureAsc: boolean = true;
  additionalCostsAsc: boolean = true;
  numberOfPersonsAsc: boolean = true;
  renterIdAsc: boolean = true;
  apartmentIdAsc: boolean = true;
  activeAsc: boolean = true;
  isActiveChecked:boolean = false;
  

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getAllLeaseContracts();
  }
  private getAllLeaseContracts(): void {
    this.http.get<any>(BACKEND_API_URL + '/lease-contracts').subscribe((response) => {
      this.rawLeaseContracts = response.leaseContracts;
      this.sortByContractId();
    });
  }

  sortByContractId() {
    this.leaseContracts = this.rawLeaseContracts.sort((n1, n2) => {
      if (this.contractIdAsc) {
        if (n1.contractId > n2.contractId) {
          return 1;
        } else if (n1.contractId < n2.contractId) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.contractId > n2.contractId) {
          return -1;
        } else if (n1.contractId < n2.contractId) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.contractIdAsc = !this.contractIdAsc;
  }

  sortByActive() {
    this.leaseContracts = this.leaseContracts.sort((n1, n2) => {
      if (this.activeAsc) {
        if (n1.active.toUpperCase() > n2.active.toUpperCase()) {
          return 1;
        } else if (n1.active.toUpperCase() < n2.active.toUpperCase()) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.active.toUpperCase() > n2.active.toUpperCase()) {
          return -1;
        } else if (n1.active.toUpperCase() < n2.active.toUpperCase()) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.activeAsc = !this.activeAsc;
  }

  sortByRenterId() {
    this.leaseContracts = this.rawLeaseContracts.sort((n1, n2) => {
      if (this.renterIdAsc) {
        if (n1.renterId > n2.renterId) {
          return 1;
        } else if (n1.renterId < n2.renterId) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.renterId > n2.renterId) {
          return -1;
        } else if (n1.renterId < n2.renterId) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.renterIdAsc = !this.renterIdAsc;
  }
  sortByApartmentId() {
    this.leaseContracts = this.rawLeaseContracts.sort((n1, n2) => {
      if (this.apartmentIdAsc) {
        if (n1.apartmentId > n2.apartmentId) {
          return 1;
        } else if (n1.apartmentId < n2.apartmentId) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.apartmentId > n2.apartmentId) {
          return -1;
        } else if (n1.apartmentId < n2.apartmentId) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.apartmentIdAsc = !this.apartmentIdAsc;
  }
  

  search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.leaseContracts = this.rawLeaseContracts;
    } else {
      this.leaseContracts = this.rawLeaseContracts.filter(
        (leaseContract) =>
          leaseContract.contractId.toString() == searchTerm.trim() ||
          leaseContract.renterId.toString() == searchTerm.trim() ||
          leaseContract.apartmentId.toString() == searchTerm.trim() ||
          leaseContract.active.toUpperCase() == searchTerm.trim().toUpperCase() 

      );
    }
  }

  filterByActive(isActiveChecked:boolean): void {
    if (isActiveChecked) {
      this.leaseContracts = this.rawLeaseContracts.filter((leaseContract) => leaseContract.active == 'J');
    } else {
      this.leaseContracts = this.rawLeaseContracts;
    }
  }
}

