import {Component, OnInit} from '@angular/core';
import {Profile} from "../../shared/models/profile.model";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {TokenStorage} from "../../shared/services/token.storage";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material";
import {ProfileService} from "../../shared/services/profile.service";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private errorMessage: string;
  private loginForm: FormGroup;
  private matcher = new ErrorStateMatcher();

  constructor(private authenticationService: AuthenticationService, private profileService: ProfileService,
              private router: Router, private token: TokenStorage) {
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  submit(): void {
    this.authenticationService.attemptAuth(this.loginForm.controls['username'].value, this.loginForm.controls['password'].value)
      .subscribe(
        data => {
          this.token.saveToken(data.token);
          this.router.navigate(['/']);
        }, err => {
          this.errorMessage = "Username or password isn't correct!"
        }
      );
  }

}
