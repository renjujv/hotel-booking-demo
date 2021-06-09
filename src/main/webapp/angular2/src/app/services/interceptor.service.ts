import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from "./auth.service";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private authenticationService:AuthenticationService) {}

  intercept(request:HttpRequest<any>, next:HttpHandler):Observable<HttpEvent<any>> {
    // if (this.authenticationService.isUserLoggedIn() && request.url.indexOf('basicauth',10) === -1) {
    if (this.authenticationService.isUserLoggedIn()) {
      console.log('Authenticated request with url: '+request.urlWithParams+' and method: '+request.method);
      const token = btoa(this.authenticationService.username+":"+this.authenticationService.password);
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic '+token
      });
      request = request.clone({headers: headers});
      return next.handle(request);
    } else {
      console.log('Failed to authenticate request with url: '+request.urlWithParams+' and method: '+request.method);
      return next.handle(request);
    }
  }
}
