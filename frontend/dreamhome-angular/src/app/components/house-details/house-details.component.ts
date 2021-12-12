import { Component, OnInit } from '@angular/core';
import { House } from 'src/app/interfaces/house';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-house-details',
  templateUrl: './house-details.component.html',
  styleUrls: ['./house-details.component.css'],
})
export class HouseDetailsComponent implements OnInit {
  public house!: House;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private snackbar: SnackbarService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getHouseByHouseNumber(params['id']);
    });
  }

  private getHouseByHouseNumber(id: any): void {
    this.http
      .get<any>(BACKEND_API_URL + '/houses/' + id)
      .subscribe((response) => {
        this.house = response.houses[0];
      });
  }

  public saveHouse(): void {
    this.house.street = (<HTMLInputElement>(
      document.querySelector('#street')
    )).value;
     this.house.hNumber = Number.parseInt((<HTMLInputElement>(
      document.querySelector('#hNumber')
    )).value);
    this.house.postcode = Number.parseInt(
      (<HTMLInputElement>document.querySelector('#postcode')).value
    );

    this.http.put<any>(BACKEND_API_URL + '/houses', this.house).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
      },
      (error) => {
        console.log(error);
      }
    );
  }

  public deleteHouse():void {
    this.http.delete<any>(BACKEND_API_URL + '/houses/' + this.house.houseNumber).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
        this.router.navigate(['/houses']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
