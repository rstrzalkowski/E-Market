import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from "./components/product-list/product-list.component";
import {HomeComponent} from "./components/home/home.component";
import {CartComponent} from "./components/cart/cart.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: HomeComponent},
  {path: 'products', component: ProductListComponent},
  {path: 'cart', component: CartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
