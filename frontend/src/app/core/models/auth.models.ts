export interface CurrentUser {
  id: string;
  firstName?: string;
  lastName?: string;
  displayName?: string;
  email: string;
  role?: string;
  soulPoints?: number;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  user?: CurrentUser;
  accessToken?: string;
}
