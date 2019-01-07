import { LocalStorageService } from './../../services/local-storage.service';
import { Component, OnInit } from '@angular/core';
import { environment as env} from './../../../environments/environment';
import { HttpMovierequestsService } from 'src/app/services/http-movierequests.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-nav-app',
  templateUrl: './nav-app.component.html',
  styleUrls: ['./nav-app.component.css']
})
export class NavAppComponent implements OnInit {
  error:string=null;
  errorEmail:string;
  userSigned;
  userEmail:string;
  nowDate:string;
  public env=env;
  constructor( private httpService:HttpMovierequestsService,
    private usersLocalstorage:LocalStorageService) { 
      setInterval(() => {
        let nowTime = new Date().toLocaleTimeString('sv-SE');
        let nowDate =new Date().toLocaleDateString('sv-SE')
        this.nowDate= nowDate+'  '+nowTime
        this.nowDate=this.nowDate.replace(/T/,"  ")
        this.nowDate=this.nowDate.slice(0,20)
      }, 1000);
    }
    resetError(){
     this.error=null;
     
    }
  ngOnInit() {
  }
  public getUser(){
    this.httpService.getUser(this.userEmail).
    subscribe(res=>{
      this.userSigned=res.body
    console.log(this.userSigned) 
    this.usersLocalstorage.addUserToLocal(this.userSigned); },(error:any)=>{
      this.error=`Error ${error.status}: that means there is no such user email`;  
     
    })
     this.userEmail==null
      }
  
 



}
