package com.dreamstream.common.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        Map<String, String> details,
        String path,
        Instant timestamp
) {
}
