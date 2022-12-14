import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {BehaviorSubject} from "rxjs";
import {Jwt} from "../model/Jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated: boolean = false;
  user: string = "";
  behaviorSubject = new BehaviorSubject<boolean>(this.authenticated);
  jwt: string = "";

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string) {
    return this.http.post<Jwt>(`${environment.apiUrl}/login`, {email, password}, {observe: 'response'});
  }

  setAuthentication(result: string | undefined, email: string | undefined) {
    if (result != undefined && email != undefined) {
      this.authenticated = true;
      this.user = email;
      this.jwt = result;
    } else {
      this.authenticated = false;
    }
    this.behaviorSubject.next(this.authenticated)
  }

  register(firstName: string, lastName: string, email: string, password: string) {
    return this.http.post(`${environment.apiUrl}/register`, {
      firstName,
      lastName,
      email,
      password
    }, {observe: 'response'});
  }

  logOut() {
    //TODO logout in backend
    this.authenticated = false;
    this.jwt = "";
    this.behaviorSubject.next(this.authenticated);
  }

  isAuthenticated() {
    return this.behaviorSubject.asObservable();
  }

}
