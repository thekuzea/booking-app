import {Component, OnInit} from '@angular/core';
import {TripService} from "../../shared/services/trip.service";
import {Trip} from "../../shared/models/trip.model";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/filter';
import {DataService} from "../../shared/services/data.service";
import {AppComponent} from "../../app.component";
import {Profile} from "../../shared/models/profile.model";

@Component({
  selector: 'app-trip-list',
  templateUrl: './trip-list.component.html',
  styleUrls: ['./trip-list.component.css']
})
export class TripListComponent implements OnInit {

  private tripList: Trip[];
  private doubledTripList: Trip[][];
  private sharedTrip: Trip;

  constructor(private tripService: TripService, private dataService: DataService,
              private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams
      .filter(params => params.search)
      .subscribe(params => {
        if (params.search === "true") {

          this.dataService.currentValue
            .subscribe(value => {
              this.sharedTrip = value;
            });

          this.tripService.getSpectified(this.sharedTrip)
            .subscribe(response => {
              if (this.sharedTrip.isOneway) {
                this.tripList = response;
              } else {
                this.doubledTripList = response;
              }
            });
        } else {
          this.tripService.findAll()
            .subscribe(data => {
              this.tripList = data;
            });
        }
      });
  }

  bookTrip(trip: Trip) {
    // trip.passengers.set(1, AppComponent.LOGGED_IN_USER);

    this.tripService.update(trip)
      .subscribe(data => {
          this.router.navigate(['trip-list'], {queryParams: {search: false}})
        },
        err => {
          console.log("Unable to book trip");
        });
  }

}
