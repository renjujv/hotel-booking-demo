import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient,HttpResponse} from "@angular/common/http"
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit{

  constructor(private http:HttpClient) {}


  public submitted:boolean;
  roomSearch: FormGroup;
  title = 'Landon Hotel';
  rooms: Room[];
  private baseUrl:string = 'http://localhost:8080';

  ngOnInit() {
    this.roomSearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl(''),

    });
    // this.rooms= ROOMS;
  }

  onSubmit({value,valid}:{value:RoomSearch,valid:boolean}) {
    console.log("clicked on submit. Updating rooms...");
    this.getAll().subscribe(
      rooms => this.rooms = rooms.content);
  }

  reserveRoom(value:string){
    console.log("Room id reserved: "+value);
  }

  getAll():Observable<any>{
  // getAll(){
    return this.http.get<any>(this.baseUrl+'/room/reservation/v1?checkin=02-01-2021&checkout=10-02-2021');
  }

  mapRoom(response:HttpResponse<any>):Room[]{
    return response.body;
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
//   {"id" : "432434", "roomNumber":"410", "price":"249", "links":""},
//   {"id" : "542653", "roomNumber":"411", "price":"250", "links":""},
//   {"id" : "756342", "roomNumber":"412", "price":"299", "links":""}
// ]
