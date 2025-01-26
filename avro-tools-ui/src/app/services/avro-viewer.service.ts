import { Injectable } from '@angular/core';
import * as avro from "avsc"
import { DecimalType } from '../utils/decimal/DecimalType';

export interface RecordMetaData {
  start: number,
  end: number
  done: boolean
}

@Injectable({
  providedIn: 'root'
})
export class AvroViewerService {

  reader: ReadableStreamDefaultReader<any> | undefined = undefined;
  metadata: any = null;
  records: any[] = []

  recordMetaData: RecordMetaData = { start: 0, end: 0, done: false }
  rowNo = 1;
  constructor() { }

  async loadAvroFile(file: File) {
    this.reader = file.stream().getReader();
  }

  async readChunks(cb: (records: any, recordMetaData: RecordMetaData) => void) {
    if (!this.reader) {
      throw new Error("reader is not defined. Load avro file first!")
    }
    const { done, value } = await this.reader!.read(); // Read the next chunk
    if (!value) {
      this.recordMetaData.done = true
      cb(this.records, this.recordMetaData);
      return;
    }
    this.records = []

    this.recordMetaData.start = this.rowNo

    var metaDecoder = avro.createBlobDecoder(new Blob([value!]), {
      parseHook: (schema) => avro.Type.forSchema(schema, { logicalTypes: { decimal: DecimalType } })
    });
    metaDecoder.on("metadata", type => {
      console.log(type)
      this.metadata = type;
    })
      .on("data", val => {
        this.records.push({ key: this.rowNo, value: val });
        this.rowNo += 1;
      })
      .on("end", () => {
        this.recordMetaData.end = this.rowNo - 1
        cb(this.records, this.recordMetaData);
        return;
      });
  }

}
