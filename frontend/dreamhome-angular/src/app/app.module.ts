import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RentersComponent } from './components/renters/renters.component';
import { NavComponent } from './components/nav/nav.component';
import { HttpClientModule } from '@angular/common/http';
import { RenterDetailsComponent } from './components/renter-details/renter-details.component';
import { ApartmentsComponent } from './components/apartments/apartments.component';
import { ApartmentDetailsComponent } from './components/apartment-details/apartment-details.component';
import { ApartmentNewComponent } from './components/apartment-new/apartment-new.component';
import { RenterNewComponent } from './components/renter-new/renter-new.component';
import { LeaseContractsComponent } from './components/lease-contracts/lease-contracts.component';
import { LeaseContractDetailsComponent } from './components/lease-contract-details/lease-contract-details.component';
import { LeaseContractNewComponent } from './components/lease-contract-new/lease-contract-new.component';
import { StatementOfBankAccountUploadComponent } from './components/statement-of-bank-account-upload/statement-of-bank-account-upload.component';
import { StatementOfBankAccountComponent } from './components/statement-of-bank-account/statement-of-bank-account.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HomeComponent } from './components/home/home.component';
import { HousesComponent } from './components/houses/houses.component';
import { HouseDetailsComponent } from './components/house-details/house-details.component';
import { HouseNewComponent } from './components/house-new/house-new.component';
import { OperatingCostStatementsComponent } from './components/operating-cost-statements/operating-cost-statements.component';
import { OpenPositionsComponent } from './components/open-positions/open-positions.component';

@NgModule({
  declarations: [
    AppComponent,
    RentersComponent,
    NavComponent,
    RenterDetailsComponent,
    ApartmentsComponent,
    ApartmentDetailsComponent,
    ApartmentNewComponent,
    RenterNewComponent,
    LeaseContractsComponent,
    LeaseContractDetailsComponent,
    LeaseContractNewComponent,
    StatementOfBankAccountUploadComponent,
    StatementOfBankAccountComponent,
    HomeComponent,
    HousesComponent,
    HouseDetailsComponent,
    HouseNewComponent,
    OperatingCostStatementsComponent,
    OpenPositionsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
