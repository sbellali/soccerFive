import { Injectable } from '@angular/core';
import { Session } from '../entities/session';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments';
@Injectable({
  providedIn: 'root'
})
export class SessionService {

    constructor(private httpClient: HttpClient) { }

    public getSessionByDate(date: string): Observable<Session[]> {
        // const params = {
        //     dateStart: date
        // }
        return this.httpClient.get<Session[]>(
            `${environment.apiUrl}/session`,
            {responseType : "json"}
        )
    }

}