import { Injectable } from "@angular/core";
import { environment } from "../../environments";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class AuthClient {
    constructor(private httpClient: HttpClient) {}

    public login(username:string, password:string): Observable<string> {
        return this.httpClient.post(
            `${environment.apiUrl}/auth/login`,
            {username, password},
            {responseType : "text"}
        );
    }

    public register(username:string, password:string): Observable<string> {
        return this.httpClient.post(
            `${environment.apiUrl}/auth/register`,
            {username, password},
            {responseType : "text"}
        ); 
    }
}