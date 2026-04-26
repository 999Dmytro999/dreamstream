package com.dreamstream.offers;

import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.dto.HelpOfferResponse;
import com.dreamstream.security.CurrentUser;
import com.dreamstream.users.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.dreamstream.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelpOfferController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.dreamstream.common.exception.GlobalExceptionHandler.class)
class HelpOfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private HelpOfferService helpOfferService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createOfferShouldReturnCreated() throws Exception {
        UUID requestId = UUID.randomUUID();
        UUID currentUserId = UUID.randomUUID();

        HelpOfferResponse response = new HelpOfferResponse(
                UUID.randomUUID(),
                requestId,
                currentUserId,
                "I can help",
                HelpOfferStatus.PENDING,
                Instant.now(),
                Instant.now()
        );

        when(helpOfferService.createOffer(eq(requestId), eq(currentUserId), any(CreateHelpOfferRequest.class)))
                .thenReturn(response);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CurrentUser(currentUserId, "offerer@example.com", UserRole.USER),
                        null
                )
        );

        mockMvc.perform(post("/api/requests/{requestId}/offers", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateHelpOfferRequest("I can help"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getMyOffersShouldReturnList() throws Exception {
        UUID currentUserId = UUID.randomUUID();
        when(helpOfferService.getMyOffers(currentUserId)).thenReturn(List.of(
                new HelpOfferResponse(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        currentUserId,
                        "Offer message",
                        HelpOfferStatus.PENDING,
                        Instant.now(),
                        Instant.now()
                )
        ));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CurrentUser(currentUserId, "offerer@example.com", UserRole.USER),
                        null
                )
        );

        mockMvc.perform(get("/api/my-offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Offer message"));
    }
}
