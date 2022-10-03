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
  productId: number;
  quantity: number;

  constructor(productId: number, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }
}
