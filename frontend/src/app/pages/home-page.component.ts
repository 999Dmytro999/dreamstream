import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';

import { RequestService } from '../core/services/request.service';
import { HelpRequestSummary } from '../core/models/help-request.models';

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.component.html'
})
export class HomePageComponent implements OnInit {
  private readonly requestService = inject(RequestService);

  requests: HelpRequestSummary[] = [];
  loading = true;

  ngOnInit(): void {
    this.requestService.listRequests().subscribe({
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

  get openCount(): number {
    return this.requests.filter((request) => request.status === 'OPEN').length;
  }

  get completedCount(): number {
    return this.requests.filter((request) => request.status === 'COMPLETED').length;
  }

  get recentRequests(): HelpRequestSummary[] {
    return [...this.requests].slice(0, 3);
  }

  statusClass(status: string): string {
    return status.toLowerCase();
  }
}
