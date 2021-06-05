import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RoomSearch} from "../book-room-form/book-room-form.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage = 'Invalid Credentials';
  successMessage:string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(private route:ActivatedRoute,
              private router:Router,
              private authenticationService:AuthenticationService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      //Validation for username and password
      username: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[_]){2,20}$")]),
      password: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[@_#$!%&*^()[],:;?]){2,20}$")]),
    });
  }

  handleLogin({value}:{value:LoginDetails}) {
    console.log('Trying to log in');
    this.authenticationService.login(value.username,value.password)
      .subscribe((result)=> {
        this.invalidLogin = false;
        this.loginSuccess = true;
        this.successMessage = 'Welcome '+this.authenticationService.getLoggedInUserName()+'Redirecting to Booking page';
        console.log('Logged in');
        setTimeout(() => {this.router.navigate(['/']);},2000);
      },() => {
        console.log('Need Authentication. Routing to login page.');
        this.router.navigate(['/login']);
        this.invalidLogin = true;
        this.loginSuccess = false;
      })
  }

  get f(){
    return this.loginForm.controls;
  }
}

export interface LoginDetails{
  username:string;
  password:string;
}
