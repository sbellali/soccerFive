import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: "",
    loadComponent: () => import("./pages/login/login.component").then((module) => module.LoginComponent),
  },
  {
    path: "home",
    loadComponent: () => import("./pages/home/home.component").then((module) => module.HomeComponent),
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
