import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Apartment } from 'src/app/interfaces/apartment';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css'],
})
export class ApartmentsComponent implements OnInit {
  apartments: Apartment[] = [];
  rawApartments: Apartment[] = [];
  apartmentNumberAsc: boolean = true;
  apartmentIdAsc: boolean = true;
  maxRenterAsc : boolean =true;
  


  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllApartments();
  }

  private getAllApartments(): void {
    this.http
      .get<any>(BACKEND_API_URL + '/apartments')
      .subscribe((response) => {
        this.rawApartments = response.apartments;
        this.sortByApartmentId();
      });
  }

  sortByApartmentId() {
    this.apartments = this.rawApartments.sort((n1, n2) => {
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
  sortByMaxRenter() {
    this.apartments = this.rawApartments.sort((n1, n2) => {
      if (this.maxRenterAsc) {
        if (n1.maxRenter > n2.maxRenter) {
          return 1;
        } else if (n1.maxRenter < n2.maxRenter) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.maxRenter > n2.maxRenter) {
          return -1;
        } else if (n1.maxRenter < n2.maxRenter) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.maxRenterAsc = !this.maxRenterAsc;
  }
  search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.apartments = this.rawApartments;
    } else {
      this.apartments = this.rawApartments.filter(
        (apartment) =>
          apartment.apartmentId.toString() == searchTerm.trim() ||
          apartment.maxRenter.toString() == searchTerm.trim() ||
          apartment.size.toString() == searchTerm.trim() ||
          apartment.apartmentNumber.toString() == searchTerm.trim() ||
          apartment.numberRooms.toString() == searchTerm.trim() ||
          apartment.securityDeposit.toString() == searchTerm.trim() ||
          apartment.houseNumber.toString() == searchTerm.trim() ||
          apartment.street.toUpperCase() == searchTerm.trim().toUpperCase() ||
          apartment.postcode.toString() == searchTerm.trim() ||
          apartment.city.toUpperCase() == searchTerm.trim().toUpperCase()
      );
    }
  }
}
