import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvroGeneratorComponent } from './avro-generator.component';

describe('AvroGeneratorComponent', () => {
  let component: AvroGeneratorComponent;
  let fixture: ComponentFixture<AvroGeneratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvroGeneratorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AvroGeneratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
