import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  numberOfCartItems: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.cartService.getProducts().subscribe((itemList) => {
      this.numberOfCartItems = itemList.length;
    })
  }

}
