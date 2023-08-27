import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    interface ErrorMessageFromBack {status: number, message: string};
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        const parsedError = {
          ...error
        };
        try {
          parsedError.error = JSON.parse(error.error);
        } catch (parseError) {
          console.log('parsing error occurred');
        }

        return throwError(parsedError);
      })
    );
  }
}
