import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  products: Product[] = [];
  totalPrice: number = 0;
  isAuthenticated: boolean = false;


  constructor(private authService: AuthService, private cartService: CartService, private router: Router) {
  }

  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((value) => {
      this.isAuthenticated = value;
    })

    this.cartService.getProducts().subscribe((productList) => {
      this.products = productList;
      this.totalPrice = this.cartService.getTotalPrice();
    })
  }

  emptyCart() {
    this.cartService.emptyCart();
  }

  deleteFromCart(product: Product) {
    this.cartService.deleteFromCart(product);
  }

  checkout() {
    if (this.isAuthenticated) {
      console.log("Checking out");
    } else {
      this.router.navigate(["/login"]);
    }
  }
}
