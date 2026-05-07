import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';

import { HelpRequestSummary } from '../core/models/help-request.models';
import { RequestService } from '../core/services/request.service';

@Component({
  selector: 'app-my-requests-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './my-requests-page.component.html'
})
export class MyRequestsPageComponent implements OnInit {
  private readonly requestService = inject(RequestService);

  requests: HelpRequestSummary[] = [];
  loading = true;
  error = '';

  ngOnInit(): void {
    this.loading = true;
    this.error = '';
    this.requestService.listMyRequests().subscribe({
      next: (requests) => {
        this.requests = requests;
        this.loading = false;
      },
      error: () => {
        this.requests = [];
        this.loading = false;
        this.error = 'Unable to load your requests right now.';
      }
    });
  }

  deleteRequest(requestId: string): void {
    if (typeof window !== 'undefined' && !window.confirm('Delete this request? This cannot be undone.')) {
      return;
    }

    this.requestService.deleteRequest(requestId).subscribe({
      next: () => this.ngOnInit(),
      error: () => {
        this.error = 'Unable to delete this request right now.';
      }
    });
  }

  statusClass(status: string): string {
    return (status || 'unknown').toLowerCase();
  }
}
