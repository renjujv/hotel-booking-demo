import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http"
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit{

  constructor(private http:HttpClientModule) {}

  public submitted:boolean;
  roomsearch: FormGroup;
  title = 'Landon Hotel';
  rooms: Room[];

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });
    this.rooms = ROOMS;
  }

  onSubmit({value,valid}:{value:RoomSearch,valid:boolean}) {
    console.log(value);
  }

  reserveRoom(value:string){
    console.log("Room id reserved: "+value);
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

var ROOMS:Room[] = [
  {
    "id":"321321",
    "roomNumber":"409",
    "price":"20",
    "links":"",
  },{
    "id":"321322",
    "roomNumber":"410",
    "price":"30",
    "links":"",
  },{
    "id":"321323",
    "roomNumber":"411",
    "price":"40",
    "links":"",
  }
]
