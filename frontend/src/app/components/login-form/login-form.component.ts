import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

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


  constructor(private authService: AuthService, private router: Router) {
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
      let password = this.loginForm.getRawValue().password;

      if (email != null && password != null) {

        this.authService.login(email.toString(), password.toString()).subscribe((result) => {

          console.log(result.status)
          if (!result.ok) {
            alert("Invalid credentials");
          } else {
            this.authService.setAuthentication(result.body?.token);
            console.log(result.body?.token);
          }

          if (this.authService.authenticated) {
            this.router.navigate(['/cart']);
          }
        })
      }
    }
  }


}
