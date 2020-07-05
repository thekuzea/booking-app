import {Profile} from "./profile.model";
import {Plane} from "./plane.model";

export class Trip {

  id: string;
  placeOfDeparture: string;
  placeOfArrival: string;
  departureDate: Date;
  returningDate: Date;
  isOneway: boolean;
  tripClass: string;
  passengers: Map<number, Profile>;
  plane: Plane;

  constructor() {
    this.passengers = new Map<number, Profile>();
  }

}
