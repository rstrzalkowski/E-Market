import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";
import {environment} from "../../environments/environment";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {
    const isApiUrl = request.url.startsWith(environment.apiUrl);

    if (this.authService.authenticated && isApiUrl) {
      request = request.clone({
          setHeaders: {Authorization: `Bearer ${this.authService.jwt}`}
        }
      )
      console.log("Added JWT to request.")
    }
    return next.handle(request);
  }
}
