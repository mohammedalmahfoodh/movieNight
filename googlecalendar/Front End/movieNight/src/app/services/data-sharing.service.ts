import { UserEvent } from './../models/UserEvent';
import { Movie } from 'src/app/models/Movie';
import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {
 
 confirmedEventMovie=new Subject<Movie>()
 private confirmedEventMovieSource=new BehaviorSubject(this.confirmedEventMovie)

  userEvent=new Subject<UserEvent>();
  movie=new Subject<any>();
  private movieSource=new BehaviorSubject(this.movie)
  private userEventSource=new BehaviorSubject(this.userEvent)

  constructor() { }
  currentConfirmedMovie=this.confirmedEventMovieSource.asObservable()
  currentMovie=this.movieSource.asObservable()
  currentUserEvent=this.userEventSource.asObservable()
  changeMovieConfirmed(movieConfirmed){
    this.confirmedEventMovieSource.next(movieConfirmed)
    }

changeMovie(movie){
this.movieSource.next(movie)
}
changeUserEvent(userEvent){
  this.userEventSource.next(userEvent)
  }


}
