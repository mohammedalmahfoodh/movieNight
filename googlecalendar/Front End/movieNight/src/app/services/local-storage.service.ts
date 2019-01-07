import { Movie } from 'src/app/models/Movie';
import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }



  public addUserToLocal(user: User): string {
    let users=this.getUsers();   
    users.push(user)     
   this.setLocalStorageUser(users);    
    return 'user added successfully'
  }
  public addAllUsers(users:User[]){
    this.setLocalStorageUser(users);
  }

  public getUsers(): User[] {
    
    let localStorageItem=JSON.parse(localStorage.getItem('users'));
    return localStorageItem==null?[]:localStorageItem.users;
  }

  private setLocalStorageUser(users:User[]):void{
    localStorage.setItem('users',JSON.stringify({users:users}));
   }
   public addMovieToLocal(movie:Movie):void{
     localStorage.setItem('movie',JSON.stringify({movie:movie}))
   }
   public getMovieFromLocal():Movie{
    let localStorageMovie=JSON.parse(localStorage.getItem('movie'));
    return localStorageMovie==null?[]:localStorageMovie.movie;
   }
}
