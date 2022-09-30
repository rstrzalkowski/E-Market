import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  getUserOrders(email: string) {
    this.http.get(`${environment.apiUrl}/orders?user=${email}`)
  }

}
