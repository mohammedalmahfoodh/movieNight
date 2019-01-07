import { Movie } from 'src/app/models/Movie';
import { UserEvent } from './../../models/UserEvent';
import { Component, OnInit } from '@angular/core';
import { DataSharingService } from 'src/app/services/data-sharing.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { User } from 'src/app/models/user';
import { HttpMovierequestsService } from 'src/app/services/http-movierequests.service';

@Component({
  selector: 'app-confirmation-event',
  templateUrl: './confirmation-event.component.html',
  styleUrls: ['./confirmation-event.component.css']
})
export class ConfirmationEventComponent implements OnInit {
  userEventCreated;
  confirmedMovie;
  imdbID: string;
  Title: string;
  imdbRating: string;
  year: string;
  Poster: string;
  users: User[];
  newUserEvent
  eventDeleted;

  constructor(private dataSharing: DataSharingService, private httpService: HttpMovierequestsService,
    private usersLocalstorage: LocalStorageService) { }

  ngOnInit() {
  this.users=this.usersLocalstorage.getUsers()
  this.dataSharing.currentUserEvent.subscribe(userEventshared=>this.newUserEvent=userEventshared)
  

   this.dataSharing.currentMovie.subscribe(movie => this.confirmedMovie = movie)
   this.Title = this.confirmedMovie.title;
   this.imdbID = this.confirmedMovie.imbdID;
   this.year = this.confirmedMovie.year;
   this.imdbRating = this.confirmedMovie.imdbRating;
   this.Poster = this.confirmedMovie.Poster;
  //  this.newUserEvent.name=this.userEventCreated.name
   // this.newUserEvent.startEvent=this.userEventCreated.
  }
  test(){
    console.log(this.newUserEvent.userStartEvent)
    console.log(this.newUserEvent.userEndEvent)
    console.log(this.users)
  }
 deleteEvent(){
   this.httpService.deleteEvent().subscribe(res=>{
    this.eventDeleted = res.body;
    console.log(this.eventDeleted.message)   
   })
 }
}
