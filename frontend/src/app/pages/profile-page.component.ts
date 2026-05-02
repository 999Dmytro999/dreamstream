import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AuthService } from '../core/services/auth.service';
import { OfferService } from '../core/services/offer.service';
import { RequestService } from '../core/services/request.service';
import { CurrentUser } from '../core/models/auth.models';

@Component({
  selector: 'app-profile-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './profile-page.component.html'
})
export class ProfilePageComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly requestService = inject(RequestService);
  private readonly offerService = inject(OfferService);

  user: CurrentUser | null = null;
  myRequestsCount = 0;
  myOffersCount = 0;
  loading = true;

  ngOnInit(): void {
    this.authService.loadCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
      }
    });

    this.requestService.listMyRequests().subscribe({
      next: (requests) => {
        this.myRequestsCount = requests.length;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });

    this.offerService.listMyOffers().subscribe({
      next: (offers) => {
        this.myOffersCount = offers.length;
      },
      error: () => {
        this.myOffersCount = 0;
      }
    });
  }
}
