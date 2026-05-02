import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { HELP_REQUEST_CATEGORIES } from '../core/models/request-options';
import { CreateHelpRequestRequest } from '../core/models/help-request.models';
import { RequestService } from '../core/services/request.service';

@Component({
  selector: 'app-create-request-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-request-page.component.html'
})
export class CreateRequestPageComponent {
  private readonly requestService = inject(RequestService);
  private readonly router = inject(Router);

  readonly categories = HELP_REQUEST_CATEGORIES;

  model: CreateHelpRequestRequest = {
    title: '',
    description: '',
    category: 'OTHER',
    location: ''
  };

  loading = false;
  error = '';

  submit(): void {
    this.loading = true;
    this.error = '';

    this.requestService.createRequest(this.model).subscribe({
      next: (request) => {
        this.loading = false;
        this.router.navigate(['/requests', request.id]);
      },
      error: () => {
        this.loading = false;
        this.error = 'Unable to create the request right now.';
      }
    });
  }
}
