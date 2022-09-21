import {Injectable} from '@angular/core';
import {Product} from "../model/Product";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: Product[] = [];
  behaviorSubject = new BehaviorSubject<Product[]>([]);

  constructor() {
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

    this.behaviorSubject.next(this.cartItems);
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
    this.behaviorSubject.next(this.cartItems);
  }

  emptyCart() {
    this.cartItems = [];
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