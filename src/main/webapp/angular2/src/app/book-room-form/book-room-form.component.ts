import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient,HttpResponse} from "@angular/common/http"
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-book-room-form',
  templateUrl: './book-room-form.component.html',
  styleUrls: ['./book-room-form.component.scss']
})

export class BookRoomFormComponent implements OnInit {
  submitted = false;
  title = 'Hotel Transylvania';
  roomSearch: FormGroup;
  rooms: Room[];
  private baseUrl:string = 'http://localhost:8080';

  constructor(private http:HttpClient) {}

  ngOnInit() {
    const now = new Date();
    this.roomSearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl(''),
    });
  }

  onSubmit({value,valid}:{value:RoomSearch,valid:boolean}) {
    console.log("clicked on submit. Updating rooms...");
    this.getAll().subscribe(
      rooms => this.rooms = rooms,
      error => console.error(error));
  }

  reserveRoom(value:string){
    console.log("Room id reserved: "+value);
  }

  getAll():Observable<any>{
    return this.http.get<any>(this.baseUrl+'/room/reservation/v1?checkin=02-01-2021&checkout=10-02-2021')
      .pipe(map(this.mapRoom));
  }

  mapRoom(response:any):Room[]{
    return response.content;
  }

  resetForm() {
    console.log('form cleared');
    this.roomSearch.reset();
    this.submitted = false;
    this.rooms = [];
  }

  get myform() { return this.roomSearch.controls; }

  getState() {
    for (let controlsKey in this.roomSearch.controls) {
      console.log(controlsKey);
    }
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

// var ROOMS:Room[] = [
//   {"id" : "323432", "roomNumber":"409", "price":"200", "links":""},
// ]

