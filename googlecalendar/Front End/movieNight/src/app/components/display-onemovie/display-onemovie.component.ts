import { Movie } from 'src/app/models/Movie';
import { UserEvent } from './../../models/UserEvent';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataSharingService } from 'src/app/services/data-sharing.service';
import { AmazingTimePickerService } from 'amazing-time-picker';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker/public_api';
import { HttpMovierequestsService } from 'src/app/services/http-movierequests.service';
import { User } from 'src/app/models/user';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { httpFactory } from '@angular/http/src/http_module';


@Component({
  selector: 'app-display-onemovie',
  templateUrl: './display-onemovie.component.html',
  styleUrls: ['./display-onemovie.component.css']
})
export class DisplayOnemovieComponent implements OnInit {
   
  panelOpenState = false;
  

  colorTheme = 'theme-red';
  bsConfig: Partial<BsDatepickerConfig>;
  posts = 1;
  usersUrl: string = 'http://localhost:8080/user/userEvents'
  constructor(private dataSharing: DataSharingService, private usersLocalstorage: LocalStorageService,
    private httpService: HttpMovierequestsService, private httpClient: HttpClient,
    private timePicker: AmazingTimePickerService) {
    
  }
  movieReceived;
  imdbID: string;
  Title: string;
  imdbRating: string;
  year: string;
  Poster: string;
  ngOnInit() {
    //this.users = this.usersLocalstorage.getUsers()
    this.getAsyncUsers()
    this.dataSharing.currentMovie.subscribe(movie => {
      this.movieReceived = movie
      
    })  
   
    this.Title = this.movieReceived.title;
    this.imdbID = this.movieReceived.imbdID;
    this.year = this.movieReceived.year;
    this.imdbRating = this.movieReceived.imdbRating;
    this.Poster = this.movieReceived.Poster;
   
   
  }
  //### get users #####
  users: User[]=[];
  async getAsyncUsers() {
    try {
      const users = await this.httpClient.get<User[]>(this.usersUrl).toPromise();
      if (users){
        this.users = users;
        this.usersLocalstorage.addAllUsers(this.users)
      } 
    } catch (error) {
      console.log('No users yet ..')
    }
  }

  //############## Time Picker ##########
  bsValue
  selectedTime = "20:28";
  open() {
    const amazingTimePicker = this.timePicker.open({
      time: this.selectedTime,
      theme: 'material-red',
      arrowStyle: {
        background: 'red',
        color: 'white'
      }
    });
    amazingTimePicker.afterClose().subscribe(time => {
      this.selectedTime = time;
      console.log(this.selectedTime)
    });
  }
  applyTheme(pop: any) {
    this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
    setTimeout(() => {
      pop.show();
    });
  }  

  //####### Create event ###############
  newMovieEvent:UserEvent=new UserEvent();
  eventValidity; 
  
 
  public createDateEvent(): string {
    let stringDate = new Date(this.bsValue).toLocaleDateString('sv-SE');
    let stringTime = this.selectedTime
    let combined = stringDate + 't' + stringTime   
    return combined;
    }

  //### start event ######
  statrtTimeDatevalidity:boolean=false;
startEventMsg:string;
startEvent: string = null;
  createStartEvent() {
   
    this.startEvent = this.createDateEvent();
    this.startEvent=this.startEvent.replace(/t/g," ")
    this.httpService.validateStartEvenntTime(this.createDateEvent()).subscribe(res => {
      this.eventValidity = res.body;
      this.startEventMsg=this.eventValidity.timevalidity
   //  console.log(this.startEventMsg)
      if(this.startEventMsg.localeCompare('valid')==0){
        this.statrtTimeDatevalidity=true;
        this.newMovieEvent.userStartEvent=this.startEvent
        this.newMovieEvent.name=this.Title
        console.log('this is movieNewstart '+this.newMovieEvent.userStartEvent)
      }else{
        this.statrtTimeDatevalidity=false
      }
     
    })
  }
  createStartEventState(): boolean {

    return  this.users.length>0 &&this.bsValue!=null;
  }
//### end event ######

  createEndEventState(): boolean {
     return  this.users.length>0 &&this.bsValue!=null && this.statrtTimeDatevalidity ;
  }

  endEvent: string = null;
  endTimeDatevalidity:boolean=false;
  endEventMsg:string;

  createEndEvent() {
    this.endEvent = this.createDateEvent();
    this.httpService.validateEndEvenntTime(this.createDateEvent()).subscribe(res => {
      this.eventValidity = res.body;
      this.endEventMsg=this.eventValidity.timevalidity
     console.log(this.endEvent)
      if(this.endEventMsg.localeCompare('valid')==0){
        this.endTimeDatevalidity=true;
        this.endEvent=this.endEvent.replace(/t/g," ")
        this.newMovieEvent.userEndEvent=this.endEvent
        console.log('this is movieNewEnd '+this.newMovieEvent.userEndEvent)
      }else{
        this.endTimeDatevalidity=false
      }     
    })   
  }
  ///##################
  sendToDisplay() {
    this.httpService.createMovieEvent().subscribe(res=>console.log(res),error=>console.log(error))
    let confirmedMovie:Movie=new Movie();
      confirmedMovie=this.movieReceived 

    this.dataSharing.changeMovieConfirmed(confirmedMovie)
    this.dataSharing.changeUserEvent(this.newMovieEvent)

  }
  //######## create event ########  
  createEventState(): boolean {   
    return this.statrtTimeDatevalidity && this.endTimeDatevalidity &&this.Title!=undefined ;
  }

  createEvent() {   
    this.httpService.createMovieEvent().subscribe((res)=>console.log(res),
    (error)=>console.log(error))
      
   this.sendToDisplay()
  }

}
