import {Plane} from "../models/plane.model";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {AppComponent} from "../../app.component";
import {Observable} from "rxjs/Observable";

@Injectable()
export class PlaneService {

  private static API_URL = "http://localhost:8080";
  private static SERVICE_URL: string = PlaneService.API_URL + "/planes/";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Plane[]> {
    return this.http.get<Plane[]>(PlaneService.SERVICE_URL);
  }

  public save(plane: Plane): Observable<Plane> {
    return this.http.post<Plane>(PlaneService.SERVICE_URL, plane)
  }

  public update(plane: Plane): Observable<Plane> {
    return this.http.put<Plane>(PlaneService.SERVICE_URL + plane.id, plane);
  }

  public delete(id: string): Observable<Plane> {
    return this.http.delete<Plane>(PlaneService.SERVICE_URL + id);
  }

}
