import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ReservationListComponent } from './reservation.component';
import { ReservationService } from './reservation.service';

@NgModule({
  declarations: [
    AppComponent,
    ReservationListComponent
  ],

  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ReservationService],
  bootstrap: [AppComponent]
})

export class AppModule { }
