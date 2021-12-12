import { Component, OnInit } from '@angular/core';
import { Apartment } from 'src/app/interfaces/apartment';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-apartment-details',
  templateUrl: './apartment-details.component.html',
  styleUrls: ['./apartment-details.component.css']
})
export class ApartmentDetailsComponent implements OnInit {
  public apartment!: Apartment;
  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private snackbar: SnackbarService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getApartmentByApartmentNumber(params['id']);
    });
  }
  private getApartmentByApartmentNumber(id: any): void {
    this.http
      .get<any>(BACKEND_API_URL + '/apartments/' + id)
      .subscribe((response) => {
        this.apartment = response.apartments[0];
      });
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

    this.http.put<any>(BACKEND_API_URL + '/apartments', this.apartment).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
      },
      (error) => {
        console.log(error);
      }
    );
  }

  public deleteApartment():void {
    this.http.delete<any>(BACKEND_API_URL + '/apartments/' + this.apartment.apartmentNumber).subscribe(
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

