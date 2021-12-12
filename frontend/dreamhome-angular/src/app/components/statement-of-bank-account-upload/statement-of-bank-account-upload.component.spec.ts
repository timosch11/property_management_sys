import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatementOfBankAccountUploadComponent } from './statement-of-bank-account-upload.component';

describe('StatementOfBankAccountUploadComponent', () => {
  let component: StatementOfBankAccountUploadComponent;
  let fixture: ComponentFixture<StatementOfBankAccountUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatementOfBankAccountUploadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StatementOfBankAccountUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
