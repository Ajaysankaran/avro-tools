import { Routes } from '@angular/router';
import { AvroViewerComponent } from './avro-viewer/avro-viewer.component';
import { AvroGeneratorComponent } from './avro-generator/avro-generator.component';

export const routes: Routes = [
    
    {path: 'viewer', component: AvroViewerComponent},
    {path: 'generator', component: AvroGeneratorComponent},
    {path: "", redirectTo: "viewer", pathMatch: "full"},
];
