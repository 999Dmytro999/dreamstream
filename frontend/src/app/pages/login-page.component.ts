import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../core/services/auth.service';
import { LoginRequest } from '../core/models/auth.models';

@Component({
  selector: 'app-login-page',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  model: LoginRequest = {
    email: '',
    password: ''
  };

  loading = false;
  error = '';

  submit(): void {
    this.loading = true;
    this.error = '';

    this.authService.login(this.model).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigateByUrl('/profile');
      },
      error: () => {
        this.loading = false;
        this.error = 'Login failed. Please check your credentials and try again.';
      }
    });
  }
}
