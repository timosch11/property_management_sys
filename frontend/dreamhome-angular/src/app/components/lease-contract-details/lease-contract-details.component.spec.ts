import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaseContractDetailsComponent } from './lease-contract-details.component';

describe('LeaseContractDetailsComponent', () => {
  let component: LeaseContractDetailsComponent;
  let fixture: ComponentFixture<LeaseContractDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeaseContractDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaseContractDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
