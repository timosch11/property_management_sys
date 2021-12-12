import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatingCostStatementsComponent } from './operating-cost-statements.component';

describe('OperatingCostStatementsComponent', () => {
  let component: OperatingCostStatementsComponent;
  let fixture: ComponentFixture<OperatingCostStatementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatingCostStatementsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatingCostStatementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
