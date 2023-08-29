import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { loggedInGuard } from './guards/logged-in.guard';

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent,
    canActivate: [loggedInGuard]
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [loggedInGuard]
  },
  {
    path: "",
    component: HomeComponent,
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
