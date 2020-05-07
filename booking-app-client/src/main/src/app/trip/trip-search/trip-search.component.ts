import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import {AuthenticationService} from "../../shared/services/authentication.service";
import {Trip} from "../../shared/models/trip.model";
import {TripService} from "../../shared/services/trip.service";
import {AppComponent} from "../../app.component";
import {DataService} from "../../shared/services/data.service";

@Component({
  selector: 'app-trip-search',
  templateUrl: './trip-search.component.html',
  styleUrls: ['./trip-search.component.css']
})
export class TripSearchComponent implements OnInit {

  private sharedTrip: Trip = new Trip();
  private searchForm: FormGroup;
  private airportList = AppComponent.AIRPORTS;

  constructor(private tripService: TripService, private dataService: DataService, private router: Router) {
  }

  ngOnInit() {
    this.searchForm = new FormGroup({
      placeOfDeparture: new FormControl(),
      placeOfArrival: new FormControl(),
      tripClass: new FormControl(),
      isOneway: new FormControl(),
      departureDate: new FormControl(),
      returningDate: new FormControl()
    });
  }

  public submit(): void {
    this.sharedTrip.placeOfDeparture = this.searchForm.controls['placeOfDeparture'].value;
    this.sharedTrip.placeOfArrival = this.searchForm.controls['placeOfArrival'].value;
    this.sharedTrip.tripClass = this.searchForm.controls['tripClass'].value;

    let date = this.searchForm.controls['departureDate'].value;
    date.setHours(2);
    this.sharedTrip.departureDate = date;

    this.sharedTrip.isOneway = this.searchForm.controls['isOneway'].value;
    if (!this.sharedTrip.isOneway) {
      date = this.searchForm.controls['returningDate'].value;
      date.setHours(2);
      this.sharedTrip.returningDate = date;
    }

    this.dataService.changeValue(this.sharedTrip);
    this.router.navigate(['/trip-list'], {queryParams: {search: true}});
  }

  private range(min: number, max: number): number[] {
    let array = [];

    for (let i = min; i != max; ++i) {
      array.push(i);
    }

    return array;
  }

}
