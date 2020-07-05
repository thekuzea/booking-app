import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Trip} from "../models/trip.model";

@Injectable()
export class DataService {

  private value = new BehaviorSubject(new Trip());
  currentValue = this.value.asObservable();

  changeValue(trip: Trip) {
    this.value.next(trip);
  }

}
