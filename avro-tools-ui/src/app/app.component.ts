import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { NgxJsonViewerModule } from 'ngx-json-viewer';
import { MenuItem } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { TabMenuModule } from 'primeng/tabmenu';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgxJsonViewerModule, TableModule, TabMenuModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.Default
})
export class AppComponent implements OnInit {
  title = 'avro-tools';

  items: MenuItem[] = [
    { label: 'Avro viewer', icon: 'pi pi-home', routerLink: 'viewer' },
    { label: 'Avro Generator', icon: 'pi pi-chart-line', routerLink: 'generator' },
  ];

  activeItem: MenuItem | undefined;

  constructor() { }

  ngOnInit(): void {
  }

  onActiveItemChange(event: MenuItem) {
    if (event) {
      this.activeItem = event;
    }
  }
}
