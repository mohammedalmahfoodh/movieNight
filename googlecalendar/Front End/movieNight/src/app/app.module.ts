import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import{HttpModule} from '@angular/http';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { NavAppComponent } from './components/nav-app/nav-app.component';
import { BokMovienightComponent } from './components/bok-movienight/bok-movienight.component';
import { DisplayOnemovieComponent } from './components/display-onemovie/display-onemovie.component';
import {MatDialogModule} from '@angular/material/dialog';
import {NgxPaginationModule} from 'ngx-pagination';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatExpansionModule} from '@angular/material/expansion';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SearchmovieComponent } from './components/searchmovie/searchmovie.component';
import { AmazingTimePickerModule } from 'amazing-time-picker';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { SignInMatDailogComponent } from './components/sign-in-mat-dailog/sign-in-mat-dailog.component';
import { ConfirmationEventComponent } from './components/confirmation-event/confirmation-event.component';
@NgModule({
  declarations: [
    AppComponent,
    NavAppComponent,
    BokMovienightComponent,
    DisplayOnemovieComponent,
    SearchmovieComponent,
    SignInMatDailogComponent,
    ConfirmationEventComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatExpansionModule,NgxPaginationModule,
    FormsModule,HttpClientModule,HttpModule,
    AmazingTimePickerModule,
    BsDatepickerModule.forRoot(),
    RouterModule.forRoot([
    { path: '', component: BokMovienightComponent },
    { path: 'displayOneMovie', component: DisplayOnemovieComponent },{ path: 'ConfirmationEvent', component: ConfirmationEventComponent }
    
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
