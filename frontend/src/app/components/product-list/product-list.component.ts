import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/Product";
import {ProductService} from "../../services/product.service";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  sortMethod: string = "createdAt,desc";

  totalPages: number = 0;
  totalPages$ = new BehaviorSubject<number>(this.totalPages)

  actualPage: number = 0;
  actualPage$ = new BehaviorSubject<number>(this.actualPage)


  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts(this.sortMethod, this.actualPage).subscribe((res) => {
      this.products = res.content;

      this.totalPages = res.totalPages;
      this.totalPages$.next(this.totalPages);

      if (this.actualPage > res.totalPages) {
        this.actualPage = 0;
        this.actualPage$.next(0)
      }
    });
  }

  searchByKeyword(keyword: string | null) {
    if (keyword === null || keyword === "") {
      this.productService.getProducts(this.sortMethod, this.actualPage).subscribe((res) => {
        this.products = res.content

        this.totalPages = res.totalPages;
        this.totalPages$.next(this.totalPages);
        this.resetToFirstPageIfNecessary();
      });
    } else {
      this.productService.searchByKeyword(keyword, this.sortMethod, this.actualPage).subscribe((res) => {
        this.products = res.content

        this.totalPages = res.totalPages;
        this.totalPages$.next(this.totalPages);
        this.resetToFirstPageIfNecessary();
      })
    }
  }

  resetToFirstPageIfNecessary() {
    if (this.actualPage > this.totalPages - 1) {
      this.actualPage = 0;
      this.actualPage$.next(0);
    }
  }

  onPagePrevious(keyword: string | null) {
    this.actualPage -= 1;
    this.searchByKeyword(keyword);
    this.actualPage$.next(this.actualPage)
  }

  onPageNext(keyword: string | null) {
    this.actualPage += 1;
    this.searchByKeyword(keyword);
    this.actualPage$.next(this.actualPage)
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
