import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LeaseContract } from 'src/app/interfaces/lease-contract';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-lease-contract-new',
  templateUrl: './lease-contract-new.component.html',
  styleUrls: ['./lease-contract-new.component.css']
})
export class LeaseContractNewComponent implements OnInit {
  public leaseContract!: LeaseContract;

  constructor(private http: HttpClient, private router: Router, private snackbar: SnackbarService) {}


  ngOnInit(): void {
    this.leaseContract= <LeaseContract>{};
  }

  public saveLeaseContract(): void {
    this.leaseContract.leaseAmount = Number.parseInt(
    (<HTMLInputElement>document.querySelector('#leaseAmount')).value
  );
  this.leaseContract.dateOfSignature = (<HTMLInputElement>document.querySelector('#dateOfSignature')).value;
  this.leaseContract.additionalCosts = Number.parseInt(
    (<HTMLInputElement>document.querySelector('#additionalCosts')).value
  );
  this.leaseContract.numberOfPersons = Number.parseInt(
    (<HTMLInputElement>document.querySelector('#numberOfPersons')).value
  );
  this.leaseContract.renterId = Number.parseInt(
    (<HTMLInputElement>document.querySelector('#renterId')).value
  );
   this.leaseContract.apartmentId = Number.parseInt(
    (<HTMLInputElement>document.querySelector('#apartmentId')).value
  );
  this.leaseContract.active = (<HTMLInputElement>document.querySelector('#active')).value;
  
    this.http.post<any>(BACKEND_API_URL + '/lease-contracts', this.leaseContract).subscribe(
      (response) => {
        this.snackbar.openSnackbar(response['message'], 'OK');
        this.router.navigate(['/lease-contracts']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}