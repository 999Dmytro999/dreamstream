import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';

import { HelpOfferSummary } from '../core/models/help-offer.models';
import { OfferService } from '../core/services/offer.service';

@Component({
  selector: 'app-my-offers-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './my-offers-page.component.html'
})
export class MyOffersPageComponent implements OnInit {
  private readonly offerService = inject(OfferService);

  offers: HelpOfferSummary[] = [];
  loading = true;

  ngOnInit(): void {
    this.offerService.listMyOffers().subscribe({
      next: (offers) => {
        this.offers = offers;
        this.loading = false;
      },
      error: () => {
        this.offers = [];
        this.loading = false;
      }
    });
  }

  statusClass(status: string): string {
    return (status || 'unknown').toLowerCase();
  }

  acceptOffer(offerId: string): void {
    this.offerService.acceptOffer(offerId).subscribe({
      next: () => this.ngOnInit()
    });
  }

  declineOffer(offerId: string): void {
    this.offerService.declineOffer(offerId).subscribe({
      next: () => this.ngOnInit()
    });
  }
}
