import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";

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

  user: SocialUser;
  loggedIn: boolean;
  private name: string;

  constructor(private route:ActivatedRoute,
              private router:Router,
              private authenticationService:AuthenticationService,
              private socialAuthService: SocialAuthService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      //Validation for username and password
      username: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[_]){2,20}$")]),
      password: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[@_#$!%&*^()[],:;?]){2,20}$")]),
    });
    this.socialAuthService.authState.subscribe((user) => {
      this.user = user;
      this.name = user.name;
      this.loggedIn = (user != null);
      if(this.loggedIn) {
        sessionStorage.setItem('Google-signin', JSON.stringify(user));
        sessionStorage.setItem('Google-loggedin-user',this.name);
      }
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Welcome '+user.firstName;
      console.log('Logged in');
      // setTimeout(() => { this.router.navigate(['/']); },5000);
    });
  }

  handleLogin({value}:{value:LoginDetails}) {
    console.log('Trying to log in');
    this.authenticationService.login(value.username,value.password)
      .subscribe((result)=> {
        this.invalidLogin = false;
        this.loginSuccess = true;
        this.successMessage = 'Welcome '+this.authenticationService.getLoggedInUserName();
        console.log('Logged in');
        setTimeout(() => {this.router.navigate(['/']);},2000);
      },() => {
        console.log('Need Authentication. Routing to login page.');
        this.router.navigate(['/login']);
        this.invalidLogin = true;
        this.loginSuccess = false;
        this.successMessage='';
      })
  }

  signInWithGoogle(): void {
    console.log('Trying google log in');
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
      ((value => {
        this.invalidLogin = false;
        this.loginSuccess = true;
        this.successMessage = 'Welcome '+this.authenticationService.getLoggedInUserName();
        console.log('Logged in');
        setTimeout(() => {this.router.navigate(['/']);},2000);
      })),
      ((reason => {
        this.invalidLogin = true;
        this.loginSuccess = false;
      }))
    )
  }

  gRefreshToken(): void {
    this.socialAuthService.refreshAuthToken(GoogleLoginProvider.PROVIDER_ID);
  }

  gSignOut(){
    this.socialAuthService.signOut();
  }

  get f(){
    return this.loginForm.controls;
  }
}

export interface LoginDetails{
  username:string;
  password:string;
}
