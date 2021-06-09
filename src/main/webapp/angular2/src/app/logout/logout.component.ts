import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";
import {SocialAuthService} from "angularx-social-login";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {
  loginSuccess = false;

  constructor(private route:ActivatedRoute,
              private router:Router,
              private authenticationService:AuthenticationService,
              private socialAuthService:SocialAuthService) {
    // console.log('Created logout page comp');
  }

  ngOnInit(): void {
    this.authenticationService.logout();
  }
}
