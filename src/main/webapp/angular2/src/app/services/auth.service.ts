import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {Observable} from "rxjs";

@Injectable()
export class AuthenticationService {
  private AUTH_URL = 'http://localhost:8080/room/reservation/v1/basicauth';
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
  GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME = 'G-User';
  GOOGLE_SIGNIN_USERNAME = 'G-Username';
  username: string;
  password: string;


  constructor(private http: HttpClient,
              private socialAuthenticator:SocialAuthService) {}

  login(username:string, password:string):Observable<AuthResponse>{
    this.username=username;
    this.password=password;
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

  getLoggedInUsername() {
    if(this.isUserLoggedIn()) return sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    else if(this.isGoogleUserLoggedIn()) return sessionStorage.getItem(this.GOOGLE_SIGNIN_USERNAME);
    else return '';
  }

  logout(){
    if(!this.isUserLoggedIn()) console.log('Not logged in.');
    else sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
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
    if(!this.isGoogleUserLoggedIn()) console.log('Not logged into google.');
    else {
      this.socialAuthenticator.signOut().then(() => {
        console.log('Signed out from google successfully. Clearing session storage...');
        sessionStorage.removeItem(this.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(this.GOOGLE_SIGNIN_USERNAME);
      },(reason) => {
        console.log('Failed to sign out from google. Reason: '+reason);
      });
    }
  }

  registerSuccessfulGLogin(gUser: SocialUser) {
    sessionStorage.setItem(this.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME, JSON.stringify(gUser));
    sessionStorage.setItem(this.GOOGLE_SIGNIN_USERNAME, gUser.email);
  }
}

export interface AuthResponse{
  message:string;
}
