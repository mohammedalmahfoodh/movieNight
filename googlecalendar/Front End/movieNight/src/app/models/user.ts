import { UserEvent } from './UserEvent';
export  class User{
    email:string;
    userEvents:UserEvent[];
     constructor(){}
     public get userEmail(){
         return this.email;
     }
     public set userEmail(_email){
      this.email=_email;
     }
     public get allUserEvents(){
        return this.userEvents;
    }
    public set userDateEvents(_userEvents:UserEvent[]){
     this.userEvents=_userEvents;
    }
    public checkEventsTimeEmpty(){

        return this.userEvents.length>0;
    }
    public addEvent(userEvent:UserEvent){
        this.userEvents.push(userEvent);
    }
}