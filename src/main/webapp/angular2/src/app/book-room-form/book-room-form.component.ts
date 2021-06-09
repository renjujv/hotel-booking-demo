import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http"
import {map} from "rxjs/operators";
import {Observable, Subscription} from "rxjs";
import {AuthenticationService} from "../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-book-room-form',
  templateUrl: './book-room-form.component.html',
  styleUrls: ['./book-room-form.component.scss']
})

export class BookRoomFormComponent implements OnInit {
  private subscriptions:Subscription;
  roomSearch: FormGroup;
  rooms: Room[];
  private baseUrl:string = 'http://localhost:8080';
  request:ReservationRoomRequest;
  currentCheckinValue:Date;
  currentCheckoutValue:Date;

  constructor(private http:HttpClient,
              private route:ActivatedRoute,
              private router:Router,
              private authenticationService:AuthenticationService) {
    // console.log('Created book room comp');
  }

  ngOnInit() {
    if((!this.authenticationService.isUserLoggedIn()) && (!this.authenticationService.isGoogleUserLoggedIn())){
      console.log('Not authenticated. Routing to login page.');
      this.router.navigate(['login']);
    } else console.log('Authenticated User');
    this.roomSearch = new FormGroup({
      //Validation for dd/mm/yyyy format using regex
      checkin: new FormControl('',
        [Validators.required,Validators.pattern("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")]),
      checkout: new FormControl('',
        [Validators.required,Validators.pattern("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")]),
    });

    //

    const roomSearchValueChanges$ = this.roomSearch.valueChanges;
    this.subscriptions = roomSearchValueChanges$.subscribe( valueChange => {
      this.currentCheckinValue = valueChange.checkin;
      this.currentCheckoutValue = valueChange.checkout;
      }
    )
  }

  onSubmit({value}:{value:RoomSearch}) {
    console.log("clicked on submit. Updating rooms...");
    this.subscriptions = this.getAll().subscribe(
      rooms => this.rooms = rooms,
      error => console.error(error));
  }

  //TODO Need to reimplement taking the checkin and checkout from form itself and not subscribed values in ngOnInit
  reserveRoom(value:string){
    this.request = new ReservationRoomRequest(value, this.currentCheckinValue,this.currentCheckoutValue);
    this.createReservation(this.request);
    console.log("Room id reserved: "+value);
    alert("Room id reserved: "+value);
  }

  createReservation(body:ReservationRoomRequest) {
    let bodyString = JSON.stringify(body);
    let headers = new HttpHeaders({'Content-Type':'application/json'});
    const options = {headers: headers};
    let posturl = this.baseUrl+'/room/reservation/v1';

    this.subscriptions = this.http.post(posturl,bodyString,options)
      .subscribe(res => console.log(res));
  }

  getAll():Observable<any>{
    let getresurl = this.baseUrl+'/room/reservation/v1?checkin='+this.currentCheckinValue+'&checkout='+this.currentCheckoutValue;
    let getallresurl = this.baseUrl+'/room/reservation/v1/all';
    return this.http.get<any>(getresurl)
      .pipe(map(this.mapRoom));
  }

  mapRoom(response:any):Room[]{
    return response.content;
  }

  resetForm() {
    console.log('form cleared');
    this.roomSearch.reset();
    this.rooms = [];
  }

  get myform() { return this.roomSearch.controls; }

  updateSampleInput() {
    const todaysdate = new Date().toISOString().split("T")[0];
    // const checkin = '02/02/2021';
    // const checkout = '22/02/2021';
    this.myform.checkin.setValue(todaysdate);
    this.myform.checkout.setValue(todaysdate);
  }

  handleLogout() {
    this.authenticationService.logout();
    this.authenticationService.gLogout();
    this.router.navigate(['/login']);
  }

  // ngOnDestroy(){
  //   this.subscriptions.unsubscribe();
  // }
}

export interface RoomSearch{
  checkin:string;
  checkout:string;
}

export interface Room{
  id:string;
  roomNumber:string;
  price:string;
  links:string;
}

export class ReservationRoomRequest{
  roomId:string;
  checkin:Date;
  checkout:Date;

  constructor(roomId:string, checkin:Date, checkout:Date) {
    this.roomId=roomId;
    this.checkin=checkin;
    this.checkout=checkout;
  }



}

