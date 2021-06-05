import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {AppComponent} from "./app.component";
import {LogoutComponent} from "./logout/logout.component";
import {BookRoomFormComponent} from "./book-room-form/book-room-form.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: BookRoomFormComponent},
  {path: 'logout', component: LogoutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
