import { Component, OnInit } from '@angular/core';
import { LeaseContract } from 'src/app/interfaces/lease-contract';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;
@Component({
  selector: 'app-lease-contract-details',
  templateUrl: './lease-contract-details.component.html',
  styleUrls: ['./lease-contract-details.component.css']
})
export class LeaseContractDetailsComponent implements OnInit {
  public leaseContract!: LeaseContract;
  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private snackbar: SnackbarService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getLeaseContractByContractId(params['id']);
  });

}
private getLeaseContractByContractId(id: any): void {
  this.http
    .get<any>(BACKEND_API_URL + '/lease-contracts/' + id)
    .subscribe((response) => {
      this.leaseContract = response.leaseContracts[0];
    });
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

  this.http.put<any>(BACKEND_API_URL + '/lease-contracts', this.leaseContract).subscribe(
    (response) => {
      this.snackbar.openSnackbar(response['message'], 'OK');
    },
    (error) => {
      console.log(error);
    }
  );
}

public deleteLeaseContract():void {
  this.http.delete<any>(BACKEND_API_URL + '/lease-contracts/' + this.leaseContract.contractId).subscribe(
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
