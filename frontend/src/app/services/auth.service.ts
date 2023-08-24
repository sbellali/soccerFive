import { Injectable } from '@angular/core';
import { AuthClient } from '../clients/auth.client';
import { Router } from '@angular/router';

const USER_STORAGE_KEY = "APP_TOKEN";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private authClient: AuthClient, private router: Router) { }

  public login(username: string, password: string): void {
    this.authClient.login(username, password).subscribe((token) => {
      localStorage.setItem(USER_STORAGE_KEY, token);
      this.router.navigateByUrl('/home')
    })
  }

  public register(username: string, password: string): void {
    this.authClient.register(username, password).subscribe((token) => this.router.navigateByUrl('/'))
  }

  public logout(): void {
    localStorage.removeItem(USER_STORAGE_KEY);
    this.router.navigateByUrl('/')
  }

  public isLoggedIn(): boolean {
    let token = localStorage.getItem(USER_STORAGE_KEY);
    return token != null && token.length > 0;
  }

  public getToken(): string | null {
    return this.isLoggedIn() ? localStorage.getItem(USER_STORAGE_KEY) : null;
  }

  
}
