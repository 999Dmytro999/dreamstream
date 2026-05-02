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

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.loading = true;
    this.error = '';

    this.requestService.listRequests(this.statusFilter || undefined).subscribe({
      next: (requests) => {
        this.requests = requests;
        this.loading = false;
      },
      error: () => {
        this.requests = [];
        this.loading = false;
        this.error = 'Unable to load requests right now.';
      }
    });
  }

  statusClass(status: string): string {
    return status.toLowerCase();
  }
}
