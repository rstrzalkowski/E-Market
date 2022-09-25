import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Product, ProductPage} from "../model/Product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  constructor(private http: HttpClient) {
  }


  getProduct(product: Product): Observable<Product> {
    return this.http.get<Product>(`${environment.apiUrl}/products/${product.id}`)
  }

  getProducts(sortMethod: string = 'dateasc', page: number): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${environment.apiUrl}/products?sort=${sortMethod}&size=8&page=${page}`);
  }

  searchByKeyword(keyword: string, sortMethod: string, page: number): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${environment.apiUrl}/products/search/${keyword}?sort=${sortMethod}&size=8&page=${page}`);
  }

}
