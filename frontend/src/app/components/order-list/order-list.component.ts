import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {Order} from "../../model/Order";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  orders: Order[] = [];
  detailsVisible: boolean = false;
  currentOrder: Order = this.orders[0];


  constructor(private orderService: OrderService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    if (this.authService.authenticated) {
      this.orderService.getUserOrders(this.authService.user).subscribe((orders) => {
        this.orders = orders;
      });
    } else {
      this.router.navigateByUrl("/login")
    }
  }

  showOrderDetails(order: Order) {
    console.log(`lol - ${order.orderDate}`)
    this.detailsVisible = true;
    this.currentOrder = order;
  }

}
