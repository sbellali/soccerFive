import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, switchMap, tap} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { USER_STORAGE_KEY, environment } from 'src/environments';
import jwtDecode from 'jwt-decode';
import { JwtPayload } from '../entities/JwtPayload';
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
  

  public login(username:string, password:string): Observable<any> {
    return this.authHttpCall('login', {username, password}).pipe(
      tap((res: {user: any, token: string}) => {
        this.storeToken(res.token);
        this.timerForToken();
        this.router.navigateByUrl('/');
      })
    )
  }

  public register(username:string, email:string, password:string): Observable<any> {
    return this.authHttpCall('register', {username, email, password}).pipe(
      switchMap(() => this.login(username, password))
    );
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


  public timerForToken() {
    if (this.isLoggedIn()) {
      const token = this.getToken();
      const tokenDecoded = jwtDecode<JwtPayload>(token);
      const now = new Date().getTime();
      const expireIn = (tokenDecoded.exp * 1000) - now;
      setTimeout(() => {
        console.log("Vous allez être deconnecter dans 3 seconde.");
      }, expireIn - 3000);
      setTimeout(() => {
        this.logout();
      }, expireIn);
    }
  }


  

}
