import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {LoginComponent} from "./signing/login/login.component";
import {RegistrationComponent} from "./signing/registration/registration.component";
import {TripSearchComponent} from "./trip/trip-search/trip-search.component";
import {TripListComponent} from "./trip/trip-list/trip-list.component";
import {AddTripComponent} from "./trip/add-trip/add-trip.component";
import {AddPlaneComponent} from "./plane/add-plane/add-plane.component";
import {WelcomeComponent} from "./main/welcome/welcome.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registration',
    component: RegistrationComponent
  },
  {
    path: '',
    component: WelcomeComponent
  },
  {
    path: 'trip-search',
    component: TripSearchComponent
  },
  {
    path: 'trip-list',
    component: TripListComponent
  },
  {
    path: 'add-trip',
    component: AddTripComponent
  },
  {
    path: 'add-plane',
    component: AddPlaneComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
