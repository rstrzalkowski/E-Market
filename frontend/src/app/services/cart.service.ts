import {Injectable} from '@angular/core';
import {Product} from "../model/Product";
import {BehaviorSubject} from "rxjs";
import {ProductService} from "./product.service";
import {Order} from "../model/Order";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: Product[] = [];
  behaviorSubject = new BehaviorSubject<Product[]>([]);

  constructor(private productService: ProductService, private http: HttpClient) {

    let cart = localStorage.getItem('cart')
    if (cart !== null) {
      this.cartItems = JSON.parse(String(cart));
      this.cartItems.forEach((item, index) => {
        this.productService.getProduct(item).subscribe((dbProduct) => {
          if (item.quantityInCart > dbProduct.amount) {
            if (dbProduct.amount === 0) {
              this.cartItems = this.cartItems.filter((p) => p.id !== item.id)
            } else {
              this.cartItems[index].quantityInCart = dbProduct.amount
            }
          }
          this.behaviorSubject.next(this.cartItems);

        }, (error) => {
          this.cartItems = this.cartItems.filter((p) => p.id !== item.id)
          this.behaviorSubject.next(this.cartItems);
          console.log(this.cartItems)
        })
      })
    }
  }

  getProducts() {
    return this.behaviorSubject.asObservable();
  }

  addToCart(product: Product) {
    let found = false;

    this.cartItems.forEach((item) => {
      if (item.id === product.id) {
        found = true;
        item.quantityInCart += 1;
      }
    })

    if (!found) {
      product.quantityInCart = 1;
      this.cartItems.push(product);
    }

    localStorage.setItem('cart', JSON.stringify(this.cartItems))
    this.behaviorSubject.next(this.cartItems);
  }

  placeOrder(order: Order) {
    return this.http.post(`${environment.apiUrl}/orders/place`, {
      userEmail: order.userEmail,
      shippingAddress: order.shippingAddress,
      products: order.products,

    }, {observe: 'response'});
  }

  deleteFromCart(product: Product) {
    this.cartItems.forEach((item) => {
      if (item.id === product.id) {
        if (item.quantityInCart === 1) {
          this.cartItems = this.cartItems.filter((item) => item.id !== product.id)
        } else {
          item.quantityInCart -= 1;
        }
      }
    })
    localStorage.setItem('cart', JSON.stringify(this.cartItems))
    this.behaviorSubject.next(this.cartItems);
  }

  emptyCart() {
    this.cartItems = [];
    localStorage.setItem('cart', JSON.stringify(this.cartItems))
    this.behaviorSubject.next(this.cartItems);
  }

  getTotalPrice() {
    let total = 0;

    this.cartItems.forEach((item) => {
      total += item.price * item.quantityInCart
    });

    return total;
  }
}
