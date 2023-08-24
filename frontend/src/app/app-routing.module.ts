import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: "",
    loadComponent: () => import("./pages/login/login.component").then((module) => module.LoginComponent),
  },
  {
    path: "home",
    loadComponent: () => import("./pages/home/home.component").then((module) => module.HomeComponent),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
