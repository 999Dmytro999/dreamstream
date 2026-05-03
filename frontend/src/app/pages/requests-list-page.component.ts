import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { HELP_REQUEST_STATUSES } from '../core/models/request-options';
import { HelpRequestStatus, HelpRequestSummary } from '../core/models/help-request.models';
import { RequestService } from '../core/services/request.service';

@Component({
  selector: 'app-requests-list-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './requests-list-page.component.html'
})
export class RequestsListPageComponent implements OnInit {
  private readonly requestService = inject(RequestService);

  readonly statuses = HELP_REQUEST_STATUSES;

  requests: HelpRequestSummary[] = [];
  statusFilter: HelpRequestStatus | '' = '';
  loading = true;
  error = '';
  private requestSequence = 0;

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(status: HelpRequestStatus | '' = this.statusFilter): void {
    const currentSequence = ++this.requestSequence;
    this.loading = true;
    this.error = '';

    this.requestService.listRequests(status || undefined).subscribe({
      next: (requests) => {
        if (currentSequence !== this.requestSequence) {
          return;
        }

        this.requests = requests;
        this.loading = false;
      },
      error: () => {
        if (currentSequence !== this.requestSequence) {
          return;
        }

        this.requests = [];
        this.loading = false;
        this.error = 'Unable to load requests right now.';
      }
    });
  }

  onStatusFilterChange(status: HelpRequestStatus | ''): void {
    this.statusFilter = status;
    this.loadRequests(status);
  }

  statusClass(status: string): string {
    return (status || 'unknown').toLowerCase();
  }
}
