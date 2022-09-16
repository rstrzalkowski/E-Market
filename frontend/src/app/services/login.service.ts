import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl, ɵRawValue} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  login(email: ɵRawValue<FormControl<string | null>>, password: ɵRawValue<FormControl<string | null>>) {
    console.log(password);
  }

}
