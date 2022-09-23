import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {ProductService} from "../../services/product.service";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  sortMethod: string = "createdAt,desc";


  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts(this.sortMethod).subscribe((res) => this.products = res);
  }

  searchByKeyword(keyword: string | null) {
    if (keyword === null || keyword === "") {
      this.productService.getProducts(this.sortMethod).subscribe((res) => this.products = res);
    } else {
      this.productService.searchByKeyword(keyword, this.sortMethod).subscribe((res) => this.products = res)
    }
  }

  changeSortingMethod(method: string | null) {
    if (method !== null) {
      switch (method) {
        case "dateasc": {
          this.sortMethod = "createdAt,asc";
          break;
        }
        case "datedesc": {
          this.sortMethod = "createdAt,desc";
          break;
        }
        case "nameasc": {
          this.sortMethod = "name,asc";
          break;
        }
        case "namedesc": {
          this.sortMethod = "name,desc";
          break;
        }
        case "priceasc": {
          this.sortMethod = "price,asc";
          break;
        }
        case "pricedesc": {
          this.sortMethod = "price,desc";
          break;
        }
      }
    }
  }
}
