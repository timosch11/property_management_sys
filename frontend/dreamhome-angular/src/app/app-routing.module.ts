import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RentersComponent } from './components/renters/renters.component';
import { RenterDetailsComponent } from './components/renter-details/renter-details.component';
import { StatementOfBankAccountUploadComponent } from './components/statement-of-bank-account-upload/statement-of-bank-account-upload.component';
import { StatementOfBankAccountComponent } from './components/statement-of-bank-account/statement-of-bank-account.component';
import { ApartmentsComponent } from './components/apartments/apartments.component';
import { LeaseContractsComponent } from './components/lease-contracts/lease-contracts.component';
import { HomeComponent } from './components/home/home.component';
import { RenterNewComponent } from './components/renter-new/renter-new.component';
import { ApartmentNewComponent } from './components/apartment-new/apartment-new.component';
import { ApartmentDetailsComponent } from './components/apartment-details/apartment-details.component';
import { LeaseContractNewComponent } from './components/lease-contract-new/lease-contract-new.component';
import { LeaseContractDetailsComponent } from './components/lease-contract-details/lease-contract-details.component';
import { HousesComponent } from './components/houses/houses.component';
import { HouseNewComponent } from './components/house-new/house-new.component';
import { HouseDetailsComponent } from './components/house-details/house-details.component';
import { OperatingCostStatementsComponent } from './components/operating-cost-statements/operating-cost-statements.component';
import { OpenPositionsComponent } from './components/open-positions/open-positions.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'renters',
    component: RentersComponent,
  },
  {
    path: 'renters/new',
    component: RenterNewComponent,
  },
  {
    path: 'renters/:id',
    component: RenterDetailsComponent,
  },
  {
    path: 'statement-of-bank-account',
    component: StatementOfBankAccountComponent,
  },
  {
    path: 'statement-of-bank-account/upload',
    component: StatementOfBankAccountUploadComponent,
  },
  {
    path: 'apartments',
    component: ApartmentsComponent,
  },
  {
    path: 'apartments/new',
    component: ApartmentNewComponent,
  },
  {
    path: 'apartments/:id',
    component: ApartmentDetailsComponent,
  },
  {
    path: 'lease-contracts',
    component: LeaseContractsComponent,
  },
  {
    path: 'lease-contracts/new',
    component: LeaseContractNewComponent,
  },
  {
    path: 'lease-contracts/:id',
    component: LeaseContractDetailsComponent,
  },
  {
    path: 'houses',
    component: HousesComponent,
  },
  {
    path: 'houses/new',
    component: HouseNewComponent,
  },
  {
    path: 'houses/:id',
    component: HouseDetailsComponent,
  },
  {
    path: 'operating-cost-statement',
    component: OperatingCostStatementsComponent,
  },
  {
    path: 'open-positions',
    component: OpenPositionsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
