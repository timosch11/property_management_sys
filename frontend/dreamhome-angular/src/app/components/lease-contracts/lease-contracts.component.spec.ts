import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaseContractsComponent } from './lease-contracts.component';

describe('LeaseContractsComponent', () => {
  let component: LeaseContractsComponent;
  let fixture: ComponentFixture<LeaseContractsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeaseContractsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaseContractsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
