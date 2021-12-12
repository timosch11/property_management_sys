import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { OperatingCostStatementRenter } from 'src/app/interfaces/operating-cost-statement-renter';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-operating-cost-statements',
  templateUrl: './operating-cost-statements.component.html',
  styleUrls: ['./operating-cost-statements.component.css'],
})
export class OperatingCostStatementsComponent implements OnInit {
  operatingCostStatementRenters: OperatingCostStatementRenter[] = [];
  rawOperatingCostStatementRenters: OperatingCostStatementRenter[] = [];
  renterIdAsc: boolean = true;
  isActiveChecked:boolean = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllRentersWithTransactions();
  }

  private getAllRentersWithTransactions(): void {
    this.http
      .get<any>(BACKEND_API_URL + '/operating-cost-statements')
      .subscribe((response) => {
        this.rawOperatingCostStatementRenters =
          response.operatingCostStatementRenters;
        this.sortByRenterId();
      });
  }

  getStatement(renterId: Number, year: Date): void {
    let params = new HttpParams()
      .set('renterId', renterId.toString())
      .set('year', year.toString());
    this.http
      .get(BACKEND_API_URL + '/operating-cost-statements/q', {
        params: params,
        responseType: 'blob',
      })
      .subscribe((response) => {
        saveAs(response, `operating_cost_statment_${renterId}_${year}.xlsx`);
      });
  }

  sortByRenterId() {
    this.operatingCostStatementRenters = this.rawOperatingCostStatementRenters.sort(
      (n1, n2) => {
        if (this.renterIdAsc) {
          if (n1.renterId > n2.renterId) {
            return 1;
          } else if (n1.renterId < n2.renterId) {
            return -1;
          } else {
            return 0;
          }
        } else {
          if (n1.renterId > n2.renterId) {
            return -1;
          } else if (n1.renterId < n2.renterId) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    );
    this.renterIdAsc = !this.renterIdAsc;
  }

   search(searchTerm: string): void {
    if (searchTerm.length == 0) {
      this.operatingCostStatementRenters = this.rawOperatingCostStatementRenters;
    } else {
      this.operatingCostStatementRenters = this.rawOperatingCostStatementRenters.filter(
        (operatingCostStatementRenter) =>
          operatingCostStatementRenter.renterId.toString() == searchTerm.trim() ||
          operatingCostStatementRenter.rFirstName == searchTerm.trim() ||
          operatingCostStatementRenter.rLastName == searchTerm.trim() ||
          operatingCostStatementRenter.year.toString() == searchTerm.trim().toUpperCase() 

      );
    }
  }

  filterByActive(isActiveChecked:boolean): void {
    if (isActiveChecked) {
      this.operatingCostStatementRenters = this.rawOperatingCostStatementRenters.filter((operatingCostStatement) => operatingCostStatement.activeRenter == 'J');
    } else {
      this.operatingCostStatementRenters = this.rawOperatingCostStatementRenters;
    }
  }
}
