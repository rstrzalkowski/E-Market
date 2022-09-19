import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  })

  token: string = "";

  constructor(private loginService: LoginService) {
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  ngOnInit(): void {
    this.loginForm.valueChanges.subscribe(console.log)
  }

  onSubmit() {

    if (this.loginForm.valid) {
      console.log("Logging in");
      let email = this.loginForm.getRawValue().email;
      let password = this.loginForm.getRawValue().password

      if (email != null && password != null) {
        this.loginService.login(email.toString(), password.toString()).subscribe((res) => {

          this.token = res.token;
          console.log(res);
        })
      }
    }

  }

}
