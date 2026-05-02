import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { HelpOfferSummary, SubmitHelpOfferRequest } from '../models/help-offer.models';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiBaseUrl}/offers`;

  listMyOffers(): Observable<HelpOfferSummary[]> {
    return this.http.get<HelpOfferSummary[]>(`${environment.apiBaseUrl}/my-offers`);
  }

  submitOffer(requestId: string, payload: SubmitHelpOfferRequest): Observable<HelpOfferSummary> {
    return this.http.post<HelpOfferSummary>(`${environment.apiBaseUrl}/requests/${requestId}/offers`, payload);
  }

  acceptOffer(offerId: string): Observable<HelpOfferSummary> {
    return this.http.put<HelpOfferSummary>(`${this.apiUrl}/${offerId}/accept`, {});
  }

  declineOffer(offerId: string): Observable<HelpOfferSummary> {
    return this.http.put<HelpOfferSummary>(`${this.apiUrl}/${offerId}/decline`, {});
  }
}
