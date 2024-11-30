import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvroViewerComponent } from './avro-viewer.component';

describe('AvroViewerComponent', () => {
  let component: AvroViewerComponent;
  let fixture: ComponentFixture<AvroViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvroViewerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AvroViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
