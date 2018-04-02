import {AppComponent} from "../../app.component";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Trip} from "../models/trip.model";
import {Injectable} from "@angular/core";

@Injectable()
export class TripService {

  private static API_URL = "http://localhost:8080";
  private static SERVICE_URL: string = TripService.API_URL + "/trips/";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Trip[]> {
    return this.http.get<Trip[]>(TripService.SERVICE_URL);
  }

  public getSpectified(trip: Trip): Observable<any> {
    return this.http.post<any>(TripService.SERVICE_URL + "specified", trip)
  }

  public save(trip: Trip): Observable<Trip> {
    return this.http.post<Trip>(TripService.SERVICE_URL, trip)
  }

  public update(trip: Trip): Observable<Trip> {
    return this.http.put<Trip>(TripService.SERVICE_URL + trip.id, trip);
  }

  public delete(id: string): Observable<Trip> {
    return this.http.delete<Trip>(TripService.SERVICE_URL + id);
  }

}
