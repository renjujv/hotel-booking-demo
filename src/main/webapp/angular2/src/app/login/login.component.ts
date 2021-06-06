import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  counter:FormControl;
  loginForm: FormGroup;
  errorMessage = 'Invalid Credentials';
  successMessage:string;
  invalidLogin:boolean;
  loginSuccess = false;
  loggedIn:boolean;
  gLoggedIn:boolean;
  gUser: SocialUser;

  constructor(private route:ActivatedRoute, private router:Router,
              private authenticationService:AuthenticationService,
              private socialAuthenticationService: SocialAuthService) {}

  ngOnInit(): void {
    this.loggedIn=false;
    this.gLoggedIn=false;
    this.loginForm = new FormGroup({
      //Validation for username and password
      username: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[_]){2,20}$")]),
      password: new FormControl('',
        [Validators.required,Validators.pattern("^([0-9]|[a-z]|[A-Z]|[@_#$!%&*^()[],:;?]){2,20}$")]),
    });

    //add gSignin Subscriber
    if(sessionStorage.getItem(this.authenticationService.GOOGLE_SIGNIN_USER_FULLNAME)){
      console.log('google already logged in');
      this.gUser = JSON.parse(sessionStorage.getItem(this.authenticationService.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME));
      this.loggedIn=true;
      this.gLoggedIn=true;
    }
    if(sessionStorage.getItem(this.authenticationService.USER_NAME_SESSION_ATTRIBUTE_NAME)){
      console.log('already logged in');
      this.loggedIn=true;
      setTimeout(()=> {
        alert('Redirecting to booking page...');
        this.router.navigate([''])
      },3000);
    }
  }

  signIn({value}:{value:LoginDetails}): void {
    console.log('Trying to log in');
    this.authenticationService.login(value.username,value.password).subscribe((response)=>{
      this.loggedIn=true;
      this.authenticationService.registerSuccessfulLogin(value.username);
      this.loginSuccess = true;
      this.invalidLogin = false;
      this.successMessage = 'Welcome '+this.authenticationService.getLoggedInUserName();
      console.log('Logged in');
      setTimeout(() => {this.router.navigate(['/']);},2000);
    },(error)=>{
      console.log('Need Authentication. Routing to login page.');
      this.router.navigate(['/login']);
      this.loginSuccess = false;
      this.loggedIn=false;
      this.invalidLogin = true;
    });
  }

  signInWithGoogle(): void {
    console.log('Trying google log in');
    this.authenticationService.gLogin().then(
      (socialUser) => {
        this.gUser = socialUser;
        this.loggedIn = true;
        this.gLoggedIn=true;
        this.loginSuccess = true;
        this.invalidLogin = false;
        this.successMessage = 'Welcome '+this.gUser.name;
        console.log('Logged in with Google');
        let googleUserFullName = socialUser.name;
        sessionStorage.setItem(this.authenticationService.GOOGLE_SIGNIN_SESSION_ATTRIBUTE_NAME, JSON.stringify(this.gUser));
        sessionStorage.setItem(this.authenticationService.GOOGLE_SIGNIN_USER_FULLNAME, googleUserFullName);
        setTimeout(()=> {
          alert('Redirecting to booking page...');
          this.router.navigate([''])
        },3000);
      },
      (reason)=>{
        console.log(reason);
        this.loggedIn = false;
        this.gLoggedIn=false;
        this.loginSuccess = false;
        this.invalidLogin = true;
        console.log('Google login failed');
      });
  }

  gRefreshToken(): void {
    this.socialAuthenticationService.refreshAuthToken(GoogleLoginProvider.PROVIDER_ID);
  }

  gSignOut(): void{
    this.authenticationService.gLogout();
    this.loggedIn=false;
    this.gLoggedIn=false;
  }

  get f(){
    return this.loginForm.controls;
  }

}

export interface LoginDetails{
  username:string;
  password:string;
}
