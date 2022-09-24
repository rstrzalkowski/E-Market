export interface ProductPage {
  content: Product[],
  totalPages: number
}

export interface Product {
  id: number,
  name: string,
  description: string,
  price: number,
  imageUrl: String,
  amount: number,
  quantityInCart: number
}
