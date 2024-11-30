import { ChangeDetectionStrategy, ChangeDetectorRef, Component, signal } from '@angular/core';
import * as avro from 'avsc';
import { NgxJsonViewerModule } from 'ngx-json-viewer';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-avro-viewer',
  standalone: true,
  imports: [NgxJsonViewerModule, TableModule],
  templateUrl: './avro-viewer.component.html',
  styleUrl: './avro-viewer.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AvroViewerComponent {

  tableRecord: any = signal([])
  selection: any = {};

  selectedObject: any = signal({key: undefined, value: {}});

  constructor(private cdref: ChangeDetectorRef) {

  }
  public onFileUpload(e: any) {
    const file = e.target.files[0];
    if (file) {
      let metadata: any = null;
      let records: any[] = []
      let i = 1;
      avro
        .createBlobDecoder(file)
        .on("metadata", type => {
          metadata = type;
        })
        .on("data", val => {
          records.push({ key: i, value: val });
          i += 1;
        })
        .on("end", () => {
          this.tableRecord.set(records)
          this.cdref.detectChanges();
        });
    }
  }

  onSelect(record: any) {
    this.selectedObject.set(record)
    this.cdref.detectChanges()
  }
}
