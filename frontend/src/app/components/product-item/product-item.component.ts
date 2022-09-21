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
  quantityInCart: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.cartService.getProducts().subscribe((productList) => {
      this.quantityInCart = 0;
      productList.forEach((item) => {
        if (item.id === this.product.id) {
          this.quantityInCart += item.quantityInCart;
        }
      })
    })

  }

  addToCart() {
    this.cartService.addToCart(this.product);

  }

}
