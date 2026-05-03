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
  private readonly accessTokenStorageKey = 'dreamstream.accessToken';

  readonly currentUser$ = this.currentUserSubject.asObservable();

  loadCurrentUser(): Observable<CurrentUser | null> {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.currentUserSubject.next(null);
      return of(null);
    }

    return this.http.get<CurrentUser>(`${environment.apiBaseUrl}/auth/me`).pipe(
      tap((user) => this.currentUserSubject.next(user)),
      catchError(() => {
        this.clearSession();
        return of(null);
      })
    );
  }

  register(payload: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiBaseUrl}/auth/register`, payload).pipe(
      tap((response) => this.storeSession(response))
    );
  }

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiBaseUrl}/auth/login`, payload).pipe(
      tap((response) => this.storeSession(response))
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${environment.apiBaseUrl}/auth/logout`, {}).pipe(
      tap(() => this.clearSession()),
      catchError(() => {
        this.clearSession();
        return of(void 0);
      })
    );
  }

  setCurrentUser(user: CurrentUser | null): void {
    this.currentUserSubject.next(user);
  }

  hasAuthenticatedSession(): boolean {
    return this.currentUserSubject.value !== null || this.getAccessToken() !== null;
  }

  getAccessToken(): string | null {
    if (typeof window === 'undefined') {
      return null;
    }

    return window.localStorage.getItem(this.accessTokenStorageKey);
  }

  private storeSession(response: AuthResponse): void {
    if (typeof window !== 'undefined') {
      if (response.accessToken) {
        window.localStorage.setItem(this.accessTokenStorageKey, response.accessToken);
      } else {
        window.localStorage.removeItem(this.accessTokenStorageKey);
      }
    }

    this.currentUserSubject.next(response.user ?? null);
  }

  private clearSession(): void {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(this.accessTokenStorageKey);
    }

    this.currentUserSubject.next(null);
  }
}
