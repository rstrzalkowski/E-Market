import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  products: Product[] = [];
  totalPrice: number = 0;


  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
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

}
