import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Profile} from "../models/profile.model";
import {AppComponent} from "../../app.component";
import 'rxjs/add/operator/map';

@Injectable()
export class ProfileService {

  private static API_URL = "http://localhost:8080";
  private static SERVICE_URL: string = ProfileService.API_URL + "/profiles/";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Profile[]> {
    return this.http.get<Profile[]>(ProfileService.SERVICE_URL);
  }

  public login(profile: Profile): Observable<Profile> {
    return this.http.post<Profile>(ProfileService.SERVICE_URL + "login", profile);
  }

  public save(profile: Profile): Observable<Profile> {
    return this.http.post<Profile>(ProfileService.SERVICE_URL + "registration", profile)
  }

  public update(profile: Profile): Observable<Profile> {
    return this.http.put<Profile>(ProfileService.SERVICE_URL + profile.username, profile);
  }

  public delete(id: string): Observable<Profile> {
    return this.http.delete<Profile>(ProfileService.SERVICE_URL + id);
  }

}
