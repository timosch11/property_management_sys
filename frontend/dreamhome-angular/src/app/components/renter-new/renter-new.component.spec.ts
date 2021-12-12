import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RenterNewComponent } from './renter-new.component';

describe('RenterNewComponent', () => {
  let component: RenterNewComponent;
  let fixture: ComponentFixture<RenterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RenterNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RenterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
