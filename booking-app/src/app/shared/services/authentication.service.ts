import { Injectable } from '@angular/core';
import { Profile } from "../models/profile.model";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map } from "rxjs/operator/map";
import { Observable } from "rxjs/Observable";
import { TokenStorage } from './token.storage';
import { Router } from '@angular/router';

@Injectable()
export class AuthenticationService {

  private static API_URL = "http://localhost:8080";

  constructor(private http: HttpClient, private tokenStorage: TokenStorage, private router: Router) {
  }

  public attemptAuth(username: string, password: string): Observable<any> {
    const credentials = { username: username, password: password };
    console.log('attempAuth ::');
    return this.http.post(AuthenticationService.API_URL + '/token/generate-token', credentials);
  }

  public logOut(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }

}