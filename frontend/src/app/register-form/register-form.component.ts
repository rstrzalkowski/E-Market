import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log("Sign up clicked");
  }

}
