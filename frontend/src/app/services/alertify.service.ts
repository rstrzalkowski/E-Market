import {Injectable} from '@angular/core';

declare let alertify: any;


@Injectable({
  providedIn: 'root'
})
export class AlertifyService {

  constructor() {
    alertify.defaults.theme.cancel = "ui black button";
  }

  info(message: string) {
    alertify.notify(message);
  }

  loginError() {
    alertify.error("Login error.").settings;
  }

  registerError() {
    alertify.error("Something went wrong.")
  }

  loginSuccess() {
    alertify.success("You are logged in.")
  }

  registerSuccess() {
    alertify.success("Account created succesfully.")
  }

  emptyCartConfirm(onYes: Function): boolean {
    return alertify.confirm("Are you sure you want to empty your cart?", function () {
      console.log("hey")
      onYes();
    })
  }

}
