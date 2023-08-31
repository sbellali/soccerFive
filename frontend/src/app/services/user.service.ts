import { Injectable } from '@angular/core';
import { Observable, map, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments';
import { User } from '../entities/user';
@Injectable({
  providedIn: 'root'
})
export class UserService {
    user: User;

    constructor(private httpClient: HttpClient) { }

    public getUser(id: number): Observable<User> {
        return this.httpClient.get<User>(
            `${environment.apiUrl}/user/${id}`,
            {responseType : "json"}
        )
    }
}