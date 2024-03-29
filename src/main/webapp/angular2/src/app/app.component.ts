import { Component } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent{
  title = 'Hotel Transylvania';

  public constructor(private titleService: Title) {
    console.log('Created root comp');
    this.titleService.setTitle(this.title)
  }
}
