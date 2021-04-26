import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadusersComponent } from './uploadusers.component';

describe('UploadusersComponent', () => {
  let component: UploadusersComponent;
  let fixture: ComponentFixture<UploadusersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadusersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadusersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
