import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../shared/services/profile.service";
import {Profile} from "../../shared/models/profile.model";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {patternValidator} from "../../shared/validation/pattern.validator";
import {ErrorStateMatcher} from "@angular/material";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private errorMessage: string;
  private profile: Profile = new Profile();
  private registrationForm: FormGroup;
  private matcher = new ErrorStateMatcher();

  constructor(private profileService: ProfileService, public router: Router) {
  }

  ngOnInit() {
    this.registrationForm = new FormGroup({
      username: new FormControl('', [Validators.required, patternValidator(/^[A-Za-z]{2,20}$/)]),
      firstName: new FormControl('', [Validators.required, patternValidator(/^[A-Za-z]{2,30}$/)]),
      lastName: new FormControl('', [Validators.required, patternValidator(/^[A-Za-z]{2,30}$/)]),
      email: new FormControl('', [Validators.required, patternValidator(/[a-z0-9._+-]+@[a-z]+\.[a-z]{2,3}$/)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl(),
      phoneNumber: new FormControl('', [Validators.required, patternValidator(/\+[0-9]{9,}$/)])
    });
  }

  submit() {
    this.profile.username = this.registrationForm.controls['username'].value;
    this.profile.firstName = this.registrationForm.controls['firstName'].value;
    this.profile.lastName = this.registrationForm.controls['lastName'].value;
    this.profile.email = this.registrationForm.controls['email'].value;
    this.profile.password = this.registrationForm.controls['password'].value;
    this.profile.phoneNumber = this.registrationForm.controls['phoneNumber'].value;

    this.profileService.save(this.profile)
      .subscribe(data => {
        this.router.navigate(['/login']);
      }, err => {
        this.errorMessage = "Username already exist";
      });
  }

}
