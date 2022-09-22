import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {AlertifyService} from "../../services/alertify.service";

declare let alertify: any;

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  products: Product[] = [];
  totalPrice: number = 0;
  isAuthenticated: boolean = false;


  constructor(private authService: AuthService,
              private cartService: CartService,
              private router: Router,
              private alertifyService: AlertifyService) {
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
    alertify.confirm("Are you sure you want to empty your cart?", () => this.cartService.emptyCart()).set({title: "Empty cart?"}).set({
      labels: {
        ok: 'Yes',
        cancel: 'Cancel'
      }
    });
  }

  deleteFromCart(product: Product) {
    this.cartService.deleteFromCart(product);
  }

  checkout() {
    if (this.isAuthenticated) {
      alert("Payment")
      console.log("Checking out");
    } else {
      this.alertifyService.info("You need to log in first.")
      this.router.navigate(["/login"]);
    }
  }
}
