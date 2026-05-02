import { Routes } from '@angular/router';

import { AppLayoutComponent } from './layout/app-layout.component';
import { HomePageComponent } from './pages/home-page.component';
import { LoginPageComponent } from './pages/login-page.component';
import { RegisterPageComponent } from './pages/register-page.component';
import { RequestsListPageComponent } from './pages/requests-list-page.component';
import { RequestDetailsPageComponent } from './pages/request-details-page.component';
import { CreateRequestPageComponent } from './pages/create-request-page.component';
import { EditRequestPageComponent } from './pages/edit-request-page.component';
import { ProfilePageComponent } from './pages/profile-page.component';
import { MyRequestsPageComponent } from './pages/my-requests-page.component';
import { MyOffersPageComponent } from './pages/my-offers-page.component';

export const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      { path: '', component: HomePageComponent, pathMatch: 'full' },
      { path: 'login', component: LoginPageComponent },
      { path: 'register', component: RegisterPageComponent },
      { path: 'requests', component: RequestsListPageComponent },
      { path: 'requests/new', component: CreateRequestPageComponent },
      { path: 'requests/:id', component: RequestDetailsPageComponent },
      { path: 'requests/:id/edit', component: EditRequestPageComponent },
      { path: 'profile', component: ProfilePageComponent },
      { path: 'my-requests', component: MyRequestsPageComponent },
      { path: 'my-offers', component: MyOffersPageComponent }
    ]
  },
  { path: '**', redirectTo: '' }
];
