import { User } from 'src/app/models/user';
import { Component, OnInit,EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpMovierequestsService } from 'src/app/services/http-movierequests.service';
import { Movie } from 'src/app/models/Movie';
import { Movies } from 'src/app/models/Movies';
import { DataSharingService } from 'src/app/services/data-sharing.service';

@Component({
  selector: 'app-searchmovie',
  templateUrl: './searchmovie.component.html',
  styleUrls: ['./searchmovie.component.css']
})
export class SearchmovieComponent implements OnInit {

  constructor(private httpService:HttpMovierequestsService,private dataSharing:DataSharingService) { }
  movie;
  movies:Movies;
  error:string=null;
  errorTitle:string=null;
  movieId:string;
  movieTitle:string;
  userEvents:any=[]
  ngOnInit() {
    this.dataSharing.currentMovie.subscribe(movieshared=>this.movie=movieshared)
  }
  public searchIdMovie(){   
      this.httpService.getMovie(this.movieId).subscribe(res=>{
        this.movie=res.body
        console.log(this.movie)
        this.sendToDisplay();
      },(error:any)=>{
        this.error=`Error ${error.status}: that means there is error in server because you entered wrong pattern of ID`;
        console.log(this.error)
      })
       //console.log(searchForm.controls.title)
     
  }
  public searchTitleMovie(){   
    this.httpService.getMovies(this.movieTitle).subscribe(res=>{
      this.movies=res.body;
      this.sendMovies();
      console.log(this.movies)
    },(error:any)=>{
      this.errorTitle=`Error ${error.status}: that means the title not exists`;
      console.log(this.errorTitle)
    })
     //console.log(searchForm.controls.title)
   
}
  resetError(){
    this.error=null;
    this.errorTitle=null;
  }
 //########## send movie to displayOne page #########
 sendToDisplay(){
  this.dataSharing.changeMovie(this.movie)    
 }


//## send to parent component bok movienight
   @Output() moviesEmmiter=new EventEmitter<Movies>();
   sendMovies(){
     this.moviesEmmiter.emit(this.movies);
   } 

   
    public testPost(){
      
      console.log(this.userEvents)
    }
//###### auto complete ##########
errorName;
moviesLength;
movieName:string="";
resetErrorName(){
  this.errorName=null;

}
getMoviesNames(){
  if(this.movieName.length>=3){
    this.httpService.getMovies(this.movieName).subscribe(
      res=>{  
        this.movies=res.body;
        console.log(res.body)
        this.sendMovies();
        this.moviesLength=this.movies.search.length;
        this.moviesLength=false;
        //this.recipesNames=res.json();
      //  this.options=res.json();
      //this.resetErrorName()
   },
   (error:any)=>{
    this.errorName=`Error ${error.status}: that means the title not exists`;
    this.moviesLength=true
    console.log(this.errorTitle)
    });
  }
  
  
 }






}
