import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from "./auth.service";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private authenticationService:AuthenticationService) {}

  intercept(request:HttpRequest<any>, next:HttpHandler):Observable<HttpEvent<any>> {
    if (this.authenticationService.isUserLoggedIn() && request.url.indexOf('basicauth',10) === -1) {
      const authReq = request.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'Basic '+'${window.btoa(this.authenticationService.username + ":" + this.authenticationService.password)}'
        })
      });
      return next.handle(authReq);
    } else {
      return next.handle(request);
    }
  }
}
