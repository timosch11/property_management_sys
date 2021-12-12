import { Component, OnInit } from '@angular/core';
import { Renter } from 'src/app/interfaces/renter';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-renter',
  templateUrl: './renters.component.html',
  styleUrls: ['./renters.component.css'],
})
export class RentersComponent implements OnInit {
  renters: Renter[] = [];
  rawRenters: Renter[] = [];
  renterIdAsc: boolean = true;
  rFirstnameAsc: boolean = true;
  rLastnameAsc: boolean = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllRenters();
  }

  private getAllRenters(): void {
    this.http.get<any>(BACKEND_API_URL + '/renters').subscribe((response) => {
      this.rawRenters = response.renters;
      this.sortByRenterId();
    });
  }

  sortByRenterId() {
    this.renters = this.rawRenters.sort((n1, n2) => {
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

  sortByFirstname() {
    this.renters = this.rawRenters.sort((n1, n2) => {
      if (this.rFirstnameAsc) {
        if (n1.rFirstname.toUpperCase() > n2.rFirstname.toUpperCase()) {
          return 1;
        } else if (n1.rFirstname.toUpperCase() < n2.rFirstname.toUpperCase()) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.rFirstname.toUpperCase() > n2.rFirstname.toUpperCase()) {
          return -1;
        } else if (n1.rFirstname.toUpperCase() < n2.rFirstname.toUpperCase()) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.rFirstnameAsc = !this.rFirstnameAsc;
  }

  sortByLastname() {
    this.renters = this.rawRenters.sort((n1, n2) => {
      if (this.rLastnameAsc) {
        if (n1.rLastname.toUpperCase() > n2.rLastname.toUpperCase()) {
          return 1;
        } else if (n1.rLastname.toUpperCase() < n2.rLastname.toUpperCase()) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.rLastname.toUpperCase() > n2.rLastname.toUpperCase()) {
          return -1;
        } else if (n1.rLastname.toUpperCase() < n2.rLastname.toUpperCase()) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.rLastnameAsc = !this.rLastnameAsc;
  }

  search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.renters = this.rawRenters;
    } else {
      this.renters = this.rawRenters.filter(
        (renter) =>
          renter.renterId.toString() == searchTerm.trim() ||
          renter.rFirstname.toUpperCase() == searchTerm.trim().toUpperCase() ||
          renter.rLastname.toUpperCase() == searchTerm.trim().toUpperCase()
      );
    }
  }
}
