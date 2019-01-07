export  class Movie{
    imdbID:string;
     Title:string;
     imdbRating:string;
     year:string;
     Poster:string;
      constructor(){}
      //**** Setters */
      set imdbId(_imdbID:string) {
        this.imdbID=_imdbID;
     }
     set title(_Title:string) {
        this.Title=_Title;
     }
     set imdBRating(_imdbRating:string) {
        this.imdbRating=_imdbRating;
     }
     set releaseYear(_year:string) {
        this.year=_year;
     }
     set poster(_poster:string) {
        this.Poster=_poster;
     }
     /////######## getters #############
  get imdbId(): string {
    return this.imdbID;
  }
  get title(): string {
    return this.Title;
  }
  get imdBRating(): string {
    return this.imdbRating;
  }
  get releaseYear(): string {
    return this.year;
  }
  get poster(): string {
    return this.Poster;
  }












}