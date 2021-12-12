import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Renter } from 'src/app/interfaces/renter';
import { Router } from '@angular/router';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-renter-new',
  templateUrl: './renter-new.component.html',
  styleUrls: ['./renter-new.component.css']
})
export class RenterNewComponent implements OnInit {

  public renter!: Renter;

  constructor(private http: HttpClient, private router: Router, private snackbar: SnackbarService) { }

  ngOnInit(): void {
    this.renter = <Renter>{};
  }

  public saveRenter():void { 
    this.renter.rFirstname = (<HTMLInputElement>document.querySelector("#rFirstname")).value;
    this.renter.rLastname = (<HTMLInputElement>document.querySelector("#rLastname")).value;
    this.renter.rGender = (<HTMLInputElement>document.querySelector("#rGender")).value;

    this.http.post<any>(BACKEND_API_URL + '/renters', this.renter).subscribe((response) => {
      this.snackbar.openSnackbar(response['message'], 'OK');
      this.router.navigate(['/renters']);
    },
    (error) => {
      console.log(error);
    }
   );
  }

}
