import { Component } from '@angular/core';

@Component({
  selector: 'app-avro-generator',
  standalone: true,
  imports: [],
  templateUrl: './avro-generator.component.html',
  styleUrl: './avro-generator.component.scss'
})
export class AvroGeneratorComponent {

  schema: any = {}

  onFileUpload(event: any) {
    const file = event.target.files[0];
    if (file) {
      // read the file content
      const reader = new FileReader();
      reader.onload = () => {
        const content = reader.result;
        // use the content as needed
        this.schema = JSON.parse(content as string);
        console.log(this.schema)
      };
      reader.readAsText(file);
    }
  }

  generateAvroFile() {
    if (this.schema.type === 'record') {
      
    }
  } 
}
