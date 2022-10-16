import {Product} from "./Product";

export class Order {
  userEmail: string;
  shippingAddress: string;
  products: OrderProduct[];
  orderDate: Date = new Date();

  constructor(userEmail: string, shippingAddress: string, products: OrderProduct[]) {
    this.userEmail = userEmail;
    this.shippingAddress = shippingAddress;
    this.products = products;
  }
}

export class OrderProduct {
  product: Product;
  quantity: number;

  constructor(productId: number, quantity: number) {
    this.product = {
      amount: 0,
      description: "",
      imageUrl: "",
      name: "",
      price: 0,
      quantityInCart: 0,
      id: productId
    };
    this.quantity = quantity;
  }
}
