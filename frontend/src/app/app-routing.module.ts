import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterFormComponent} from "./register-form/register-form.component";
import {LoginFormComponent} from "./components/login-form/login-form.component";

const routes: Routes = [
  {path: '', component: RegisterFormComponent},
  {path: 'login', component: LoginFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
