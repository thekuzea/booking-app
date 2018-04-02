import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  ErrorStateMatcher, MatCardModule, MatCheckboxModule, MatDatepickerModule, MatDividerModule, MatIconModule,
   MatInputModule, MatMenuModule, MatNativeDateModule, MatOptionModule, MatRadioModule, MatSelectModule, 
   MatToolbarModule, ShowOnDirtyErrorStateMatcher
} from "@angular/material";
import { AppComponent } from './app.component';
import { Interceptor } from "./app.interceptor";
import { AppRoutingModule } from "./app.routing";
import { WelcomeComponent } from './main/welcome/welcome.component';
import { AddPlaneComponent } from './plane/add-plane/add-plane.component';
import { AuthenticationService } from "./shared/services/authentication.service";
import { DataService } from "./shared/services/data.service";
import { PlaneService } from "./shared/services/plane.service";
import { ProfileService } from "./shared/services/profile.service";
import { TokenStorage } from "./shared/services/token.storage";
import { TripService } from "./shared/services/trip.service";
import { LoginComponent } from './signing/login/login.component';
import { RegistrationComponent } from './signing/registration/registration.component';
import { AddTripComponent } from './trip/add-trip/add-trip.component';
import { TripListComponent } from './trip/trip-list/trip-list.component';
import { TripSearchComponent } from './trip/trip-search/trip-search.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    TripSearchComponent,
    TripListComponent,
    AddTripComponent,
    AddPlaneComponent,
    WelcomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatDividerModule,
    MatCardModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule
  ],
  providers: [
    ProfileService,
    TripService,
    PlaneService,
    AuthenticationService,
    DataService,
    TokenStorage,
    {
      provide: ErrorStateMatcher, 
      useClass: ShowOnDirtyErrorStateMatcher 
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
