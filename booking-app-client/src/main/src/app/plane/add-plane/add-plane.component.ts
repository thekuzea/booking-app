import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Plane} from "../../shared/models/plane.model";
import {ErrorStateMatcher} from "@angular/material";
import {PlaneService} from "../../shared/services/plane.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-plane',
  templateUrl: './add-plane.component.html',
  styleUrls: ['./add-plane.component.css']
})
export class AddPlaneComponent implements OnInit {

  private plane = new Plane();
  private addPlaneForm: FormGroup;
  private matcher = new ErrorStateMatcher();

  constructor(private planeService: PlaneService, private router: Router) { }

  ngOnInit() {
    this.addPlaneForm = new FormGroup({
      title: new FormControl('', Validators.required)
    });
  }

  submit() {
    this.plane.name = this.addPlaneForm.controls['title'].value;

    this.planeService.save(this.plane).subscribe(
      response => {
        this.router.navigate(['/']);
      },
      err => {
        console.log("Can't add plane");
      }
    );
  }

}
