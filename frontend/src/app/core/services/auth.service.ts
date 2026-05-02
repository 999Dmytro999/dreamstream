import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, catchError, of, tap } from 'rxjs';

import { environment } from '../../../environments/environment';
import { AuthResponse, CurrentUser, LoginRequest, RegisterRequest } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly currentUserSubject = new BehaviorSubject<CurrentUser | null>(null);

  readonly currentUser$ = this.currentUserSubject.asObservable();

  loadCurrentUser(): Observable<CurrentUser | null> {
    return this.http.get<CurrentUser>(`${environment.apiBaseUrl}/auth/me`).pipe(
      tap((user) => this.currentUserSubject.next(user)),
      catchError(() => {
        this.currentUserSubject.next(null);
        return of(null);
      })
    );
  }

  register(payload: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiBaseUrl}/auth/register`, payload).pipe(
      tap((response) => this.currentUserSubject.next(response.user ?? null))
    );
  }

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiBaseUrl}/auth/login`, payload).pipe(
      tap((response) => this.currentUserSubject.next(response.user ?? null))
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${environment.apiBaseUrl}/auth/logout`, {}).pipe(
      tap(() => this.currentUserSubject.next(null)),
      catchError(() => {
        this.currentUserSubject.next(null);
        return of(void 0);
      })
    );
  }

  setCurrentUser(user: CurrentUser | null): void {
    this.currentUserSubject.next(user);
  }
}
