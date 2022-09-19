import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Jwt} from "../model/Jwt";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string) {
    return this.http.post<Jwt>(`${environment.apiUrl}/login`, {email, password});
  }

  register(firstName: string, lastName: string, email: string, password: string) {
    return this.http.post(`${environment.apiUrl}/register`, {
      firstName,
      lastName,
      email,
      password
    }, {observe: 'response'});
  }

}
