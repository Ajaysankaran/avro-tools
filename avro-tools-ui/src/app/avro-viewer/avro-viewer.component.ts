import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Signal, WritableSignal, signal } from '@angular/core';
import * as avro from 'avsc';
import { NgxJsonViewerModule } from 'ngx-json-viewer';
import { TableModule } from 'primeng/table';
import { DecimalType } from '../utils/decimal/DecimalType';
import { AvroViewerService, RecordMetaData } from '../services/avro-viewer.service';

@Component({
  selector: 'app-avro-viewer',
  standalone: true,
  imports: [NgxJsonViewerModule, TableModule],
  templateUrl: './avro-viewer.component.html',
  styleUrl: './avro-viewer.component.scss',
  changeDetection: ChangeDetectionStrategy.Default
})
export class AvroViewerComponent {

  tableRecord: any = signal([])
  selection: any = {};

  selectedObject: any = signal({ key: undefined, value: {} });

  recordMetaData: WritableSignal<RecordMetaData> = signal({ start: 0, end: 0, done: false })

  constructor(private cdref: ChangeDetectorRef, private avroViewerService: AvroViewerService) {

  }
  public async onFileUpload(e: any) {
    this.selectedObject.set({ key: undefined, value: {} })
    const file = e.target.files[0];
    if (file) {
      await this.avroViewerService.loadAvroFile(file);
      this.nextChunk()
    }
  }

  async nextChunk() {
    await this.avroViewerService.readChunks((records: any, metadata: RecordMetaData) => {
      this.tableRecord.set(records)
      this.recordMetaData.set(metadata)
      this.triggerChangeDetection()
    })
  }

  onSelect(record: any) {
    this.selectedObject.set(record)
    this.cdref.detectChanges()
  }

  triggerChangeDetection() {
    this.cdref.detectChanges();
  }
}
