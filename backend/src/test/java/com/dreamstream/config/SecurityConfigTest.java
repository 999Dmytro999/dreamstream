package com.dreamstream.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.pattern.PathPatternParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.server.PathContainer.parsePath;

class SecurityConfigTest {

    @Test
    void requestOfferPathPatternShouldParseAndMatchUuidStylePaths() {
        var parser = new PathPatternParser();

        var pattern = assertDoesNotThrow(() -> parser.parse(SecurityConfig.REQUEST_OFFER_PATH_PATTERN));

        assertTrue(pattern.matches(parsePath("/api/requests/123e4567-e89b-12d3-a456-426614174000/offers")));
    }
}
