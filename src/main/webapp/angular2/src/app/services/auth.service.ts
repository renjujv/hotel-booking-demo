import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { map } from 'rxjs/operators';

@Injectable()
export class AuthenticationService {
  // BASE_PATH: 'http://localhost:8080'
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

  public username: String;
  public password: String;

  constructor(private http: HttpClient) { }

  login(username:string, password:string){
    const headers = new HttpHeaders({ Authorization: AuthenticationService.createBasicAuthToken(username,password)});
    return this.http.get<AuthResponse>(`http://localhost:8080/room/reservation/v1/basicauth`,
      { headers: headers}).pipe(map((res) => {
        this.username = username;
        this.password = password;
        this.registerSuccessfulLogin(username);
    }))
  }

  private static createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  private registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)
  }

  logout(){
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    return user !== null;

  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }
}

export interface AuthResponse{
  message:string;
}
