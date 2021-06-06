import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ReactiveFormsModule, FormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { BookRoomFormComponent } from './book-room-form/book-room-form.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthenticationService } from "./services/auth.service";
import { HttpInterceptorService } from "./services/interceptor.service";
import {RouterModule} from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { SocialLoginModule, SocialAuthServiceConfig} from 'angularx-social-login';
import { GoogleLoginProvider} from "angularx-social-login";

// start social login config
const googleLoginOptions = {
  scope: 'profile email'
};
// https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiauth2clientconfig

let config = [
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider("Google-OAuth-Client-Id", googleLoginOptions)
  }
];

@NgModule({
  declarations: [
    AppComponent,
    BookRoomFormComponent,
    LoginComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([]),
    AppRoutingModule,
    SocialLoginModule
  ],
  providers: [AuthenticationService,
    HttpInterceptorService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '1087724114731-n6uts3opnrcdnevkbl854ovprc246alm.apps.googleusercontent.com'
            )
          }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
