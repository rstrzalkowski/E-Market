import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  numberOfCartItems: number = 0;

  isAuthenticated: boolean = false;

  constructor(private authService: AuthService, private cartService: CartService) {
  }

  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((value) => {
      this.isAuthenticated = value;
    })

    this.cartService.getProducts().subscribe((itemList) => {
      this.numberOfCartItems = itemList.length;
    })
  }

  logOut() {
    this.authService.logOut();
  }

}
