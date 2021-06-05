import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/auth.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {
  loginSuccess = false;

  constructor(private route:ActivatedRoute,
              private router:Router,
              private authenticationService:AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.logout();
  }
}
