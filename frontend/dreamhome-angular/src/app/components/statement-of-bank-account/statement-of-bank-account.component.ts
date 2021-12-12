import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { StatementOfBankAccount } from 'src/app/interfaces/statement-of-bank-account';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-statement-of-bank-account',
  templateUrl: './statement-of-bank-account.component.html',
  styleUrls: ['./statement-of-bank-account.component.css'],
})
export class StatementOfBankAccountComponent implements OnInit {
  statementsOfBankAccount: StatementOfBankAccount[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllStatmentsOfBankAccount();
  }

  private getAllStatmentsOfBankAccount(): void {
    this.http
      .get<any>(BACKEND_API_URL + '/statement-of-bank-account')
      .subscribe((response) => {
        this.statementsOfBankAccount = response.statementsOfBankAccount;
      });
  }
}
