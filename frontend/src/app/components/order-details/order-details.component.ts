import {Component, Input, OnInit} from '@angular/core';
import {Order} from "../../model/Order";

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  @Input()
  order!: Order;

  constructor() {
  }

  ngOnInit(): void {
    console.log("Showing order details");
  }

}
