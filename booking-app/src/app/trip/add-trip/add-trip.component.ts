import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Trip} from "../../shared/models/trip.model";
import {TripService} from "../../shared/services/trip.service";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {PlaneService} from "../../shared/services/plane.service";
import {Plane} from "../../shared/models/plane.model";

@Component({
  selector: 'app-add-trip',
  templateUrl: './add-trip.component.html',
  styleUrls: ['./add-trip.component.css']
})
export class AddTripComponent implements OnInit {

  private trip: Trip = new Trip();
  private planeList: Plane[];
  private addTripForm: FormGroup;
  private airportList = AppComponent.AIRPORTS;

  constructor(private tripService: TripService, private planeService: PlaneService,
              private router: Router) {
  }

  ngOnInit() {
    this.addTripForm = new FormGroup({
      placeOfDeparture: new FormControl(),
      placeOfArrival: new FormControl(),
      tripClass: new FormControl(),
      departureDate: new FormControl(),
      plane: new FormControl()
    });

    this.planeService.findAll()
      .subscribe(response => {
        this.planeList = response;
      })
  }

  public submit(): void {
    this.trip.placeOfDeparture = this.addTripForm.controls['placeOfDeparture'].value;
    this.trip.placeOfArrival = this.addTripForm.controls['placeOfArrival'].value;
    this.trip.tripClass = this.addTripForm.controls['tripClass'].value;
    this.trip.departureDate = this.addTripForm.controls['departureDate'].value;
    this.trip.plane = this.addTripForm.controls['plane'].value;

    this.tripService.save(this.trip).subscribe();
    this.router.navigate(['/trip-list'], {queryParams: {search: 'false'}});
  }
}
