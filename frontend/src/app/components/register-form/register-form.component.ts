import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  registerForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  })

  constructor(private loginService: LoginService) {
  }

  get firstName() {
    return this.registerForm.get('firstName');
  }

  get lastName() {
    return this.registerForm.get('lastName');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  ngOnInit(): void {
    this.registerForm.valueChanges.subscribe(console.log)
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log("Signing up");

      let firstName = this.registerForm.getRawValue().firstName;
      let lastname = this.registerForm.getRawValue().lastName;
      let email = this.registerForm.getRawValue().email;
      let password = this.registerForm.getRawValue().password;

      if (firstName != null && lastname != null && email != null && password != null) {
        this.loginService.register(firstName.toString(), lastname.toString(), email.toString(), password.toString()).subscribe((res) => {
          console.log(res.status)
        })
      }
    }
  }


}
