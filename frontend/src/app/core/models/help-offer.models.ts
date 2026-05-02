export type HelpOfferStatus = 'PENDING' | 'ACCEPTED' | 'DECLINED' | 'CANCELLED';

export interface HelpOfferSummary {
  id: string;
  helpRequestId: string;
  helpRequestTitle?: string;
  offeredByName?: string;
  message: string;
  status: HelpOfferStatus;
  createdAt?: string;
  updatedAt?: string;
}

export interface SubmitHelpOfferRequest {
  message: string;
}
