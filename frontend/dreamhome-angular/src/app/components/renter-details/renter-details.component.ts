import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Renter } from 'src/app/interfaces/renter';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-renter-details',
  templateUrl: './renter-details.component.html',
  styleUrls: ['./renter-details.component.css'],
})
export class RenterDetailsComponent implements OnInit {
  public renter!: Renter;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router, private snackbar: SnackbarService) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getRenterById(params['id']);
    });
  }

  private getRenterById(id: any): void {
    this.http
      .get<any>(BACKEND_API_URL + '/renters/' + id)
      .subscribe((response) => {
        this.renter = response.renters[0];
      });
  }
  
  public saveRenter():void {    
    this.renter.rFirstname = (<HTMLInputElement>document.querySelector("#rFirstname")).value;
    this.renter.rLastname = (<HTMLInputElement>document.querySelector("#rLastname")).value;
    this.renter.rGender = (<HTMLInputElement>document.querySelector("#rGender")).value;
    
    this.http.put<any>(BACKEND_API_URL + '/renters', this.renter).subscribe((response) => {
      this.snackbar.openSnackbar(response['message'], 'OK');
    },
    (error) => {
      console.log(error);
    }
   );
  }
  public deleteRenter():void {
    this.http.delete<any>(BACKEND_API_URL + '/renters/' + this.renter.renterId).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
        this.router.navigate(['/renters']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
