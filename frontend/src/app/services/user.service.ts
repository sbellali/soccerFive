import { Injectable } from '@angular/core';
import { Observable, map, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments';
import { User } from '../entities/user';
import { AuthService } from './auth.service';
import jwtDecode from 'jwt-decode';
import { JwtPayload } from '../entities/JwtPayload';
@Injectable({
  providedIn: 'root'
})
export class UserService {
    user: User;

    constructor(private httpClient: HttpClient, private authService: AuthService) { }

    public getUserFromJwt(): User {
        const token = this.authService.getToken();
        const tokenDecoded = jwtDecode<JwtPayload>(token);
        return tokenDecoded.user;
    }

    public getUser(id: number): Observable<User> {
        return this.httpClient.get<User>(
            `${environment.apiUrl}/user/${id}`,
            {responseType : "json"}
        )
    }
}