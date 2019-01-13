import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { environment as env} from './../../../environments/environment';
import { Movie } from 'src/app/models/Movie';
import { Movies } from 'src/app/models/Movies';
import { DataSharingService } from 'src/app/services/data-sharing.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
@Component({
  selector: 'app-bok-movienight',
  templateUrl: './bok-movienight.component.html',
  styleUrls: ['./bok-movienight.component.css']
})
export class BokMovienightComponent implements OnInit {
  movies=new Movies();
  movieTodisplay;
  Title=null;
 
  p: number = 1;
  collection: Movie[] =this.movies.search; 
  public env=env;
  constructor(private dataSharing:DataSharingService,private movieLocalstorage: LocalStorageService ) {
    if(this.Title==null){
      console.log(this.Title)
      this.movieTodisplay=this.movieLocalstorage.getMovieFromLocal()
    }
   }
  
  ngOnInit() {
    this.dataSharing.currentMovie.subscribe(movie => {      
      this.movieTodisplay = movie
      this.Title=this.movieTodisplay.title;
      
    })
   
  }

  receiveMovies($event){
   this.movies=$event;
  }
//########## send movie to displayOne page #########
sendToDisplay(){
  this.dataSharing.changeMovie(this.movieTodisplay)    
 }
  
 //####### send movie from array of movies #####
 sendFromArray(movie){
  this.dataSharing.changeMovie(movie)    
 }
}
