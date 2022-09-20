import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {

  @Input() product!: Product;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  addToCart() {
    this.cartService.addToCart(this.product);
  }

}
