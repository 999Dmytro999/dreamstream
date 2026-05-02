import { HelpRequestCategory, HelpRequestStatus } from './help-request.models';

export const HELP_REQUEST_STATUSES: readonly HelpRequestStatus[] = [
  'OPEN',
  'IN_PROGRESS',
  'COMPLETED',
  'CANCELLED'
];

export const HELP_REQUEST_CATEGORIES: readonly HelpRequestCategory[] = [
  'EDUCATION',
  'TECHNOLOGY',
  'FOOD',
  'HOUSING',
  'TRANSPORTATION',
  'JOB_SEARCH',
  'DOCUMENTS',
  'OTHER'
];
