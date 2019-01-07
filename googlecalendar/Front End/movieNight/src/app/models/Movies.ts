import { Movie } from "./Movie";

export  class Movies{
    search:Movie[];
    Search:Movie[];
    constructor(){}
    
    public get Movies(){
        return this.Search;
    }
    public set Movies(movies:Movie[]){
     this.Search=movies;
    }
    public get Moviessearch(){
        return this.search;
    }

}