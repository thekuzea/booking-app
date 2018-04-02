import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  private welcomeMessage: string = "Hello and welcome here. You book - we fly, lucky or unlucky.";

  constructor() {
  }

  ngOnInit() {
  }

}
