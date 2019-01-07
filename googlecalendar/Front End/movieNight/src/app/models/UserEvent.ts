 export class UserEvent{
      name:string;
      startEvent:string;
      endEvent:string;
      newEvent:string;
      constructor(){}
      public get userName(){
          return this.name;
      }
      public set userName(_name){
       this.name=_name;
      }
      public get userEndEvent(){
        return this.endEvent;
    }
    public set userEndEvent(_endEvent){
     this.endEvent=_endEvent;
    }
    public get userStartEvent(){
        return this.startEvent;
    }
    public set userStartEvent(_startEvent){
     this.startEvent=_startEvent;
    }
    public get usernewEvent(){
        return this.newEvent;
    }
    public set usernewEvent(_newEvent){
     this.newEvent=_newEvent;
    }






}