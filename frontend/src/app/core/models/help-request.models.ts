export type HelpRequestStatus = 'OPEN' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export type HelpRequestCategory =
  | 'EDUCATION'
  | 'TECHNOLOGY'
  | 'FOOD'
  | 'HOUSING'
  | 'TRANSPORTATION'
  | 'JOB_SEARCH'
  | 'DOCUMENTS'
  | 'OTHER';

export interface HelpRequestSummary {
  id: string;
  title: string;
  description?: string;
  category: HelpRequestCategory;
  location?: string;
  status: HelpRequestStatus;
  createdByName?: string;
  helperName?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface HelpRequestDetails extends HelpRequestSummary {
  createdById?: string;
  helperId?: string;
}

export interface CreateHelpRequestRequest {
  title: string;
  description: string;
  category: HelpRequestCategory;
  location: string;
}

export interface UpdateHelpRequestRequest extends CreateHelpRequestRequest {}
