import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Apartment } from 'src/app/interfaces/apartment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-apartment-new',
  templateUrl: './apartment-new.component.html',
  styleUrls: ['./apartment-new.component.css']
})
export class ApartmentNewComponent implements OnInit {
  public apartment!: Apartment;
  constructor(private http: HttpClient, private router: Router, private snackbar: SnackbarService) { }

  ngOnInit(): void {
    this.apartment = <Apartment>{};
  }
  public saveApartment(): void {
    this.apartment.maxRenter = Number.parseInt((<HTMLInputElement>(
      document.querySelector('#maxRenter'))).value
    );
    this.apartment.size = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#size')).value
    );
    this.apartment.apartmentNumber = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#apartmentNumber')).value
    );
    this.apartment.numberRooms = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#numberRooms')).value
    );
    this.apartment.securityDeposit = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#securityDeposit')).value
    );
    this.apartment.houseNumber = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#houseNumber')).value
    );

    this.http.post<any>(BACKEND_API_URL + '/apartments', this.apartment).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
        this.router.navigate(['/apartments']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}

