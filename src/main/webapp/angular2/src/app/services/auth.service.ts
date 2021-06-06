import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { map } from 'rxjs/operators';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {Observable} from "rxjs";

@Injectable()
export class AuthenticationService {
  private AUTH_URL = 'http://localhost:8080/room/reservation/v1/basicauth';
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
  GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME = 'Google-signin';
  GOOGLE_SIGNIN_USER_FULLNAME = 'Google-loggedin-user';
  gUser:SocialUser;

  constructor(private http: HttpClient,
              private socialAuthenticator:SocialAuthService) { }

  login(username:string, password:string):Observable<AuthResponse>{
    //create auth header
    const headers = new HttpHeaders({
      Authorization: AuthenticationService.createBasicAuthToken(username,password)});
    // Checking authentication with Spring security
    return this.http.get<AuthResponse>(this.AUTH_URL,
      { headers: headers});
  }

  private static createBasicAuthToken(username:string, password:string) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    return user !== null;
  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME) ||
      sessionStorage.getItem(this.GOOGLE_SIGNIN_USER_FULLNAME);
    if (user === null) return ''
    return user;
  }

  logout(){
    if(sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)){
      sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    }
  }

  //Google sign in provider
  gLogin():Promise<SocialUser> {
    return this.socialAuthenticator.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  isGoogleUserLoggedIn() {
    let user = sessionStorage.getItem(this.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME);
    return user !== null;
  }

  gLogout(){
    this.socialAuthenticator.signOut().then(() => {
      console.log('Signed out from google successfully');
      },(reason) => {
      console.log('Failed to sign out from google. Reason: '+reason);
    });
    if (sessionStorage.getItem(this.GOOGLE_SIGNIN_USER_FULLNAME)) {
      console.log('clearing session storage...')
      sessionStorage.removeItem(this.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME);
      sessionStorage.removeItem(this.GOOGLE_SIGNIN_USER_FULLNAME);
    }
  }

}

export interface AuthResponse{
  message:string;
}
