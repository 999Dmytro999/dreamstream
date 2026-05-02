import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { HELP_REQUEST_CATEGORIES } from '../core/models/request-options';
import { HelpRequestDetails, UpdateHelpRequestRequest } from '../core/models/help-request.models';
import { RequestService } from '../core/services/request.service';

@Component({
  selector: 'app-edit-request-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './edit-request-page.component.html'
})
export class EditRequestPageComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly requestService = inject(RequestService);
  private readonly router = inject(Router);

  readonly categories = HELP_REQUEST_CATEGORIES;

  requestId = '';
  model: UpdateHelpRequestRequest = {
    title: '',
    description: '',
    category: 'OTHER',
    location: ''
  };

  loading = true;
  saving = false;
  error = '';

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.loading = false;
      this.error = 'Missing request id.';
      return;
    }

    this.requestId = id;
    this.requestService.getRequest(id).subscribe({
      next: (request) => {
        this.model = this.toFormModel(request);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Unable to load the request.';
      }
    });
  }

  submit(): void {
    this.saving = true;
    this.error = '';

    this.requestService.updateRequest(this.requestId, this.model).subscribe({
      next: (request) => {
        this.saving = false;
        this.router.navigate(['/requests', request.id]);
      },
      error: () => {
        this.saving = false;
        this.error = 'Unable to save the request right now.';
      }
    });
  }

  private toFormModel(request: HelpRequestDetails): UpdateHelpRequestRequest {
    return {
      title: request.title,
      description: request.description ?? '',
      category: request.category,
      location: request.location ?? ''
    };
  }
}
