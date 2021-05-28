import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http"
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-book-room-form',
  templateUrl: './book-room-form.component.html',
  styleUrls: ['./book-room-form.component.scss']
})

export class BookRoomFormComponent implements OnInit {
  title = 'Hotel Transylvania';
  roomSearch: FormGroup;
  rooms: Room[];
  private baseUrl:string = 'http://localhost:8080';
  request:ReservationRoomRequest;
  currentCheckinValue:Date;
  currentCheckoutValue:Date;

  constructor(private http:HttpClient) {}

  ngOnInit() {
    this.roomSearch = new FormGroup({
      //Validation for dd/mm/yyyy format using regex
      checkin: new FormControl('',
        [Validators.required]),
      checkout: new FormControl('',
        [Validators.required]),
    });

    //Validators.pattern("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$")

    const roomSearchValueChanges$ = this.roomSearch.valueChanges;
    roomSearchValueChanges$.subscribe( valueChange => {
      this.currentCheckinValue = valueChange.checkin;
      this.currentCheckoutValue = valueChange.checkout;
      }
    )
  }

  onSubmit({value}:{value:RoomSearch}) {
    console.log("clicked on submit. Updating rooms...");
    this.getAll().subscribe(
      rooms => this.rooms = rooms,
      error => console.error(error));
  }

  //TODO Need to reimplement taking the checkin and checkout from form itself and not subscribed values
  reserveRoom(value:string){
    this.request = new ReservationRoomRequest(value, this.currentCheckinValue,this.currentCheckoutValue);
    this.createReservation(this.request);
    console.log("Room id reserved: "+value);
  }

  createReservation(body:ReservationRoomRequest) {
    let bodyString = JSON.stringify(body);
    let headers = new HttpHeaders({'Content-Type':'application/json'});
    const options = {headers: headers};

    this.http.post(this.baseUrl+'/room/reservation/v1',bodyString,options)
      .subscribe(res => console.log(res));
  }

  getAll():Observable<any>{
    return this.http.get<any>(this.baseUrl+'/room/reservation/v1?checkin='+this.currentCheckinValue+'&checkout='+this.currentCheckoutValue)
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

