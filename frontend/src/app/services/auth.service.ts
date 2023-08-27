import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { USER_STORAGE_KEY, environment } from 'src/environments';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private httpClient: HttpClient) { }


  private authHttpCall(prefix: string, body: any): Observable<any> {
    return this.httpClient.post(
        `${environment.apiUrl}/auth/${prefix}`,
        body,
        {responseType : "json"}
    );
  }

  public login(username:string, password:string): void {
    this.authHttpCall('login', {username, password}).subscribe({
        next: (res: {user: any, token: string}) => {
          this.storeToken(res.token)
          this.router.navigateByUrl('/');
        }
    })
  }

  public register(username:string, email:string, password:string): void {
    this.authHttpCall('register', {username, email, password}).subscribe({
      next: () => {
        this.login(username, password)
      }
  })
  }

  public logout(): void {
    localStorage.removeItem(USER_STORAGE_KEY);
    this.router.navigateByUrl('/login')
  }

  public isLoggedIn(): boolean {
    let token = localStorage.getItem(USER_STORAGE_KEY);
    return token != null && token.length > 0;
  }

  public getToken(): string | null {
    return this.isLoggedIn() ? localStorage.getItem(USER_STORAGE_KEY) : null;
  }

  public storeToken(token: any): void {
    localStorage.setItem(USER_STORAGE_KEY, token);
  }
}
