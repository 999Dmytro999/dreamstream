package com.dreamstream.helprequests.dto;

import java.util.UUID;

public record HelpRequestCreatedByResponse(
        UUID id,
        String firstName,
        String lastName
) {
}
