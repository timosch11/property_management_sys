import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatementOfBankAccountComponent } from './statement-of-bank-account.component';

describe('StatementOfBankAccountComponent', () => {
  let component: StatementOfBankAccountComponent;
  let fixture: ComponentFixture<StatementOfBankAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatementOfBankAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StatementOfBankAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
