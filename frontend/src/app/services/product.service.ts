import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ProductPage} from "../model/Product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  constructor(private http: HttpClient) {
  }

  getProducts(sortMethod: string, page: number): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${environment.apiUrl}/products?sort=${sortMethod}&size=8&page=${page}`);
  }

  searchByKeyword(keyword: string, sortMethod: string, page: number): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${environment.apiUrl}/products/search/${keyword}?sort=${sortMethod}&size=8&page=${page}`);
  }

}
