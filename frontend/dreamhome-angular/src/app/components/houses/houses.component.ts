import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { House } from 'src/app/interfaces/house';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-houses',
  templateUrl: './houses.component.html',
  styleUrls: ['./houses.component.css'],
})
export class HousesComponent implements OnInit {
  houses: House[] = [];
  rawHouses: House[] = [];
  houseNumberAsc: boolean = true;
  hNumberAsc: boolean =true;
  streetAsc: boolean = true;
  postcodeAsc: boolean = true;
  cityAsc: boolean = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllHouses();
  }

  private getAllHouses(): void {
    this.http.get<any>(BACKEND_API_URL + '/houses').subscribe((response) => {
      this.rawHouses = response.houses;
      this.sortByHouseNumber();
    });
  }

  sortByHouseNumber() {
    this.houses = this.rawHouses.sort((n1, n2) => {
      if (this.houseNumberAsc) {
        if (n1.houseNumber > n2.houseNumber) {
          return 1;
        } else if (n1.houseNumber < n2.houseNumber) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.houseNumber > n2.houseNumber) {
          return -1;
        } else if (n1.houseNumber < n2.houseNumber) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.houseNumberAsc = !this.houseNumberAsc;
  }

  sortByHNumber() {
    this.houses = this.rawHouses.sort((n1, n2) => {
      if (this.hNumberAsc) {
        if (n1.hNumber > n2.hNumber) {
          return 1;
        } else if (n1.hNumber < n2.hNumber) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.hNumber > n2.hNumber) {
          return -1;
        } else if (n1.hNumber < n2.hNumber) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.hNumberAsc = !this.hNumberAsc;
  }

  sortByStreet() {
    this.houses = this.rawHouses.sort((n1, n2) => {
      if (this.streetAsc) {
        if (n1.street.toUpperCase() > n2.street.toUpperCase()) {
          return 1;
        } else if (n1.street.toUpperCase() < n2.street.toUpperCase()) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.street.toUpperCase() > n2.street.toUpperCase()) {
          return -1;
        } else if (n1.street.toUpperCase() < n2.street.toUpperCase()) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.streetAsc = !this.streetAsc;
  }

  sortByPostCode() {
    this.houses = this.rawHouses.sort((n1, n2) => {
      if (this.postcodeAsc) {
        if (n1.postcode > n2.postcode) {
          return 1;
        } else if (n1.postcode < n2.postcode) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.postcode > n2.postcode) {
          return -1;
        } else if (n1.postcode < n2.postcode) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.postcodeAsc = !this.postcodeAsc;
  }

  sortByCity() {
    this.houses = this.rawHouses.sort((n1, n2) => {
      if (this.cityAsc) {
        if (n1.city.toUpperCase() > n2.city.toUpperCase()) {
          return 1;
        } else if (n1.city.toUpperCase() < n2.city.toUpperCase()) {
          return -1;
        } else {
          return 0;
        }
      } else {
        if (n1.city.toUpperCase() > n2.city.toUpperCase()) {
          return -1;
        } else if (n1.city.toUpperCase() < n2.city.toUpperCase()) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    this.cityAsc = !this.cityAsc;
  }

  search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.houses = this.rawHouses;
    } else {
      this.houses = this.rawHouses.filter(
        (house) =>
          house.houseNumber.toString() == searchTerm.trim() ||
          house.street.toUpperCase() == searchTerm.trim().toUpperCase() ||
          house.postcode.toString() == searchTerm.trim() ||
          house.city.toUpperCase() == searchTerm.trim().toUpperCase()
      );
    }
  }
}
