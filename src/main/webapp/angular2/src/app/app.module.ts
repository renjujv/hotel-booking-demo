import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { BookRoomFormComponent } from './book-room-form/book-room-form.component';

@NgModule({
  declarations: [
    AppComponent,
    BookRoomFormComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent,BookRoomFormComponent]
})
export class AppModule {}
