import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { OfferService } from '../core/services/offer.service';
import { RequestService } from '../core/services/request.service';
import { HelpRequestDetails } from '../core/models/help-request.models';

@Component({
  selector: 'app-request-details-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './request-details-page.component.html'
})
export class RequestDetailsPageComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly requestService = inject(RequestService);
  private readonly offerService = inject(OfferService);

  request: HelpRequestDetails | null = null;
  loading = true;
  error = '';
  offerMessage = '';
  actionMessage = '';

  ngOnInit(): void {
    this.loadRequest();
  }

  loadRequest(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      this.loading = false;
      this.error = 'Missing request id.';
      return;
    }

    this.loading = true;
    this.error = '';

    this.requestService.getRequest(id).subscribe({
      next: (request) => {
        this.request = request;
        this.loading = false;
      },
      error: () => {
        this.request = null;
        this.loading = false;
        this.error = 'Unable to load this request.';
      }
    });
  }

  submitOffer(): void {
    const id = this.request?.id;
    if (!id) {
      return;
    }

    this.actionMessage = '';

    this.offerService.submitOffer(id, { message: this.offerMessage }).subscribe({
      next: () => {
        this.offerMessage = '';
        this.actionMessage = 'Your offer was sent.';
      },
      error: () => {
        this.actionMessage = 'Unable to send your offer right now.';
      }
    });
  }

  markComplete(): void {
    const id = this.request?.id;
    if (!id) {
      return;
    }

    this.requestService.completeRequest(id).subscribe({
      next: () => this.loadRequest(),
      error: () => {
        this.actionMessage = 'Unable to complete this request right now.';
      }
    });
  }

  statusClass(status: string): string {
    return status.toLowerCase();
  }
}
