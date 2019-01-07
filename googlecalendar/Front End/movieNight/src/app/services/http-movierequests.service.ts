import { User } from 'src/app/models/user';
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subject, of } from 'rxjs';
import { Movie } from '../models/Movie';
import { Movies } from '../models/Movies';
import { map, catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class HttpMovierequestsService {
  headers;
  movies :Movie[]=[];
  userUrl:string='http://localhost:8080/user/';
  movieUrl:string='http://localhost:8080/movie/';
  moviesUrl:string='http://localhost:8080/movie/all/';
  moviesNamesUrl:string='http://localhost:8080/movie/names/'  
  addNewEventUrl:string='http://localhost:8080/user/testgetuser'
  cheackStartDateTimeValidityUrl:string='http://localhost:8080/user/checkstarteventtime/'
  cheackEndDateTimeValidityUrl:string='http://localhost:8080/user/checkendeventtime/'
  createMovieEventUrl:string='http://localhost:8080/user/createmovieevent/'
  deleteEventUrl:string='http://localhost:8080/user/deletemovieevent'

  constructor(private http:HttpClient) {
    

   }

  public getMovie(movieId:string):Observable<HttpResponse<Movie>>{
   return this.http.get<Movie>(this.movieUrl+movieId,{ observe: 'response' });
  }

  public getMovieNames(movieName:string):Observable<HttpResponse<string>>{
    return this.http.get<string>(this.moviesNamesUrl+movieName,{ observe: 'response' });
   }
  public getMovies(movieTitle:string):Observable<HttpResponse<Movies>>{
    return this.http.get<Movies>(this.moviesUrl+movieTitle,{ observe: 'response' });
   }
///###############################
   
   public validateStartEvenntTime(dateTime:string):Observable<HttpResponse<string>>{
    return this.http.get<string>(this.cheackStartDateTimeValidityUrl+dateTime,{ observe: 'response' })
   }
   public validateEndEvenntTime(dateTime:string):Observable<HttpResponse<string>>{
    return this.http.get<string>(this.cheackEndDateTimeValidityUrl+dateTime,{ observe: 'response' })
   }
   public deleteEvent():Observable<HttpResponse<string>>{
    return this.http.get<string>(this.deleteEventUrl,{ observe: 'response' })
   }
   public createMovieEvent(){
    return this.http.get<string>(this.createMovieEventUrl,{ responseType: 'text' as 'json' })
   }


    //get user events
    public getUser(email:string):Observable<HttpResponse<User>>{
      return this.http.get<User>(this.userUrl+email,{ observe: 'response' });
     }

     private handleError<T> (operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {   
          //  console.error(error); // log to console instead            
        console.log(`${operation} failed: ${error.message}`);   
        
        return of(result as T);
      };
    }
     private extractData(res: Response) {
      let body = res;
      return body || { };
    }
    
     public addNewEvent(time:string):Observable<HttpResponse<string>>{
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': ['application/json','text/plain' ]   
          
        })  
                    
      };
     // const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
      httpOptions.headers.append('Access-Control-Allow-Origin', 'http://localhost:8080')
      httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
      httpOptions.headers.append('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
      httpOptions.headers.append('Access-Control-Allow-Credentials', 'true');

      return this.http.post<string>(this.addNewEventUrl, time,httpOptions).pipe(
     
        map(this.extractData),
        catchError(this.handleError<string>('checkEventDateValidity'))
      );


     }

     
     


}
