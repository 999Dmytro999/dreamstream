import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

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

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.loading = true;
    this.error = '';

    this.requestService
      .listRequests(this.statusFilter || undefined)
      .pipe(finalize(() => {
        this.loading = false;
      }))
      .subscribe({
      next: (requests) => {
        this.requests = requests;
      },
      error: () => {
        this.requests = [];
        this.error = 'Unable to load requests right now.';
      }
    });
  }

  onStatusFilterChange(): void {
    this.loadRequests();
  }

  statusClass(status: string): string {
    return (status || 'unknown').toLowerCase();
  }
}
