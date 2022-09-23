import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Product} from "../model/Product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  constructor(private http: HttpClient) {
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.apiUrl}/products?sort=createdAt,desc&size=8`);
  }

  searchByKeyword(keyword: string) {
    return this.http.get<Product[]>(`${environment.apiUrl}/products/search/${keyword}?sort=createdAt,desc&size=8`);
  }

}
