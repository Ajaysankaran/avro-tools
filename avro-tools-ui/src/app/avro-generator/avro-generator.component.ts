import { Component } from '@angular/core';
import generateAvroRecord from './avro-record-faker';
import { FormsModule } from '@angular/forms';
import * as streamSaver from 'streamsaver';
import * as avro from 'avsc';

@Component({
  selector: 'app-avro-generator',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './avro-generator.component.html',
  styleUrl: './avro-generator.component.scss'
})
export class AvroGeneratorComponent {

  schema: any;

  limit: number = 10

  constructor() {

  }

  onFileUpload(event: any) {
    const file = event.target.files[0];
    if (file) {
      // read the file content
      const reader = new FileReader();
      reader.onload = () => {
        const content = reader.result;
        // use the content as needed
        this.schema = JSON.parse(content as string);
      };
      reader.readAsText(file);
    }
  }


  generateAvroFile() {
    const encoder = new avro.streams.BlockEncoder(this.schema as avro.Type)
    const fileStream = streamSaver.createWriteStream(this.schema!.name + '.avro');
    const writer = fileStream.getWriter();
    for (let i = 0; i < this.limit; i++) {
      const record = generateAvroRecord(this.schema as any);
      encoder.write(record);
    }
    encoder.end();
    encoder.pipe(writer);
  } 
}
