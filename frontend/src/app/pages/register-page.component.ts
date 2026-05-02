import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../core/services/auth.service';
import { RegisterRequest } from '../core/models/auth.models';

@Component({
  selector: 'app-register-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register-page.component.html'
})
export class RegisterPageComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  model: RegisterRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };

  loading = false;
  error = '';

  submit(): void {
    this.loading = true;
    this.error = '';

    this.authService.register(this.model).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigateByUrl('/profile');
      },
      error: () => {
        this.loading = false;
        this.error = 'Registration failed. Please review the form and try again.';
      }
    });
  }
}
