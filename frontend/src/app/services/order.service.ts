import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Order} from "../model/Order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  getUserOrders(email: string) {
    return this.http.get<Order[]>(`${environment.apiUrl}/orders?user=${email}`)
  }

}
