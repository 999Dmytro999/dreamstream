package com.dreamstream.offers.dto;

import com.dreamstream.offers.HelpOfferStatus;

import java.time.Instant;
import java.util.UUID;

public record HelpOfferResponse(
        UUID id,
        UUID helpRequestId,
        UUID offeredBy,
        String message,
        HelpOfferStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
