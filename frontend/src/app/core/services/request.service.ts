import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import {
  CreateHelpRequestRequest,
  HelpRequestDetails,
  HelpRequestStatus,
  HelpRequestSummary,
  UpdateHelpRequestRequest
} from '../models/help-request.models';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiBaseUrl}/requests`;

  listRequests(status?: HelpRequestStatus): Observable<HelpRequestSummary[]> {
    let params = new HttpParams();

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<HelpRequestSummary[]>(this.apiUrl, { params });
  }

  listMyRequests(): Observable<HelpRequestSummary[]> {
    return this.http.get<HelpRequestSummary[]>(`${environment.apiBaseUrl}/my-requests`);
  }

  getRequest(id: string): Observable<HelpRequestDetails> {
    return this.http.get<HelpRequestDetails>(`${this.apiUrl}/${id}`);
  }

  createRequest(payload: CreateHelpRequestRequest): Observable<HelpRequestDetails> {
    return this.http.post<HelpRequestDetails>(this.apiUrl, payload);
  }

  updateRequest(id: string, payload: UpdateHelpRequestRequest): Observable<HelpRequestDetails> {
    return this.http.put<HelpRequestDetails>(`${this.apiUrl}/${id}`, payload);
  }

  deleteRequest(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  completeRequest(id: string): Observable<HelpRequestDetails> {
    return this.http.post<HelpRequestDetails>(`${this.apiUrl}/${id}/complete`, {});
  }

  volunteerForRequest(id: string, message = ''): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/volunteer`, { message });
  }
}
