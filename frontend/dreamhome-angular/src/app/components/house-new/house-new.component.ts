import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { House } from 'src/app/interfaces/house';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-house-new',
  templateUrl: './house-new.component.html',
  styleUrls: ['./house-new.component.css'],
})
export class HouseNewComponent implements OnInit {
  public house!: House;

  constructor(private http: HttpClient, private router: Router, private snackbar: SnackbarService) {}

  ngOnInit(): void {
    this.house = <House>{};
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

    this.http.post<any>(BACKEND_API_URL + '/houses', this.house).subscribe(
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
