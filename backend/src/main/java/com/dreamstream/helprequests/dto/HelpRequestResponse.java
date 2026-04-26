package com.dreamstream.helprequests.dto;

import com.dreamstream.helprequests.HelpRequestCategory;
import com.dreamstream.helprequests.HelpRequestStatus;

import java.time.Instant;
import java.util.UUID;

public record HelpRequestResponse(
        UUID id,
        String title,
        String description,
        HelpRequestCategory category,
        String location,
        HelpRequestStatus status,
        UUID ownerId,
        Instant createdAt,
        Instant updatedAt
) {
}
