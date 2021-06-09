import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  private subscriptions:Subscription;
  private usernameRegex = '^([0-9]|[a-z]|[A-Z]|[_]){2,20}$';
  private passwordRegex = '^([0-9]|[a-z]|[A-Z]|[@_#$!%&*^()[],:;?]){2,20}$'
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
              private socialAuthenticationService: SocialAuthService) {
    // console.log('Created login page comp');
  }

  ngOnInit(): void {
    this.loggedIn = this.gLoggedIn = false;
    this.loginForm = new FormGroup({
      //Validation for username and password
      username: new FormControl('',
        [Validators.required,Validators.pattern(this.usernameRegex)]),
      password: new FormControl('',
        [Validators.required,Validators.pattern(this.passwordRegex)]),
    });

    //redirect if authenticated
    if(this.authenticationService.isUserLoggedIn()){
      this.loggedIn = true;
      console.log('Already logged in.');
      if(this.authenticationService.isGoogleUserLoggedIn()){
        console.log('Using Google sign-in.');
        this.gLoggedIn=true;
      }
      this.redirectAfterTimeoutWithAlert('',2000,'Redirected to landing page');
    }
  }

  signIn({value}:{value:LoginDetails}): void {
    console.log('Trying to log in...');
    this.subscriptions = this.authenticationService.login(value.username,value.password)
      .subscribe((response) => {
        this.authenticationService.setJSessionToken(response);
        console.log('response: ');
        console.dir(response);
        this.loggedIn = this.loginSuccess = true;
        this.gLoggedIn = this.invalidLogin = false;
        this.authenticationService.registerSuccessfulLogin(value.username);
        this.successMessage = 'Welcome '+this.authenticationService.getLoggedInUsername();
        console.log('Logged in');
        this.redirectAfterTimeoutWithAlert('',2000,'Redirected to landing page');
        },(error) => {
        console.log('Login failed. Reason for failure:');
        console.dir(error);
        this.loginSuccess = this.loggedIn=false;
        this.invalidLogin = true;
      });
  }

  signInWithGoogle(): void {
    console.log('Trying google log in...');
    this.authenticationService.gLogin().then(
      (socialUser) => {
        this.gUser = socialUser;
        this.loginSuccess = this.loggedIn = this.gLoggedIn = true;
        this.successMessage = 'Welcome '+this.gUser.name;
        this.authenticationService.registerSuccessfulGLogin(this.gUser);
        console.log('Logged in with Google.');
        this.redirectAfterTimeoutWithAlert('',2000,'Redirected to landing page');
      },(reason) => {
        this.loggedIn = this.gLoggedIn = this.loginSuccess = false;
        this.invalidLogin = true;
        console.log('Google login failed. Reason: '+reason);
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

  redirectAfterTimeoutWithAlert(path:string,milliseconds:number,successMessage:string){
    setTimeout(() => {
        this.router.navigate([path])
          .then((navigated)=> {
            if(navigated) console.log(successMessage);
            else console.error('Failed to navigate to " '+path+'".');
            },
            (error)=>{console.log('Failed to route to " '+path+'". Reason: '+error);})
      }
      ,milliseconds);
  }

  // ngOnDestroy(){
  //   console.log('Unsubscribing from login component observer.');
  //   this.subscriptions.unsubscribe();
  // }

}

export interface LoginDetails{
  username:string;
  password:string;
}
