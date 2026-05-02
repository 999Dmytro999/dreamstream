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

  ngOnInit(): void {
    this.requestService.listMyRequests().subscribe({
      next: (requests) => {
        this.requests = requests;
        this.loading = false;
      },
      error: () => {
        this.requests = [];
        this.loading = false;
      }
    });
  }

  statusClass(status: string): string {
    return status.toLowerCase();
  }
}
