import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {ProductService} from "../../services/product.service";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = []

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts().subscribe((res) => this.products = res);
  }

  searchByKeyword(keyword: string | null) {
    if (keyword === null || keyword === "") {
      this.productService.getProducts().subscribe((res) => this.products = res);
    } else {
      this.productService.searchByKeyword(keyword).subscribe((res) => this.products = res)
    }
  }
}
