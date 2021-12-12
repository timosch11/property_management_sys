import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaseContractNewComponent } from './lease-contract-new.component';

describe('LeaseContractNewComponent', () => {
  let component: LeaseContractNewComponent;
  let fixture: ComponentFixture<LeaseContractNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeaseContractNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaseContractNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
