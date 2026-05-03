package com.dreamstream.helprequests;

import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestCreatedByResponse;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dreamstream.security.CurrentUser;
import com.dreamstream.users.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelpRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.dreamstream.common.exception.GlobalExceptionHandler.class)
class HelpRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private HelpRequestService service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAllShouldReturnList() throws Exception {
        HelpRequestResponse response = new HelpRequestResponse(
                UUID.randomUUID(),
                "Need transport",
                "Need a ride to clinic",
                HelpRequestCategory.TRANSPORTATION,
                "Denver",
                HelpRequestStatus.OPEN,
                new HelpRequestCreatedByResponse(UUID.randomUUID(), "Jane", "Doe"),
                Instant.now(),
                Instant.now()
        );

        when(service.getAll(null)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Need transport"));
    }

    @Test
    void getAllShouldPassStatusFilter() throws Exception {
        HelpRequestResponse response = new HelpRequestResponse(
                UUID.randomUUID(),
                "Need transport",
                "Need a ride to clinic",
                HelpRequestCategory.TRANSPORTATION,
                "Denver",
                HelpRequestStatus.OPEN,
                new HelpRequestCreatedByResponse(UUID.randomUUID(), "Jane", "Doe"),
                Instant.now(),
                Instant.now()
        );

        when(service.getAll(HelpRequestStatus.OPEN)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/requests").param("status", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Need transport"));
    }

    @Test
    void getMyRequestsShouldReturnList() throws Exception {
        HelpRequestResponse response = new HelpRequestResponse(
                UUID.randomUUID(),
                "Need transport",
                "Need a ride to clinic",
                HelpRequestCategory.TRANSPORTATION,
                "Denver",
                HelpRequestStatus.OPEN,
                new HelpRequestCreatedByResponse(UUID.randomUUID(), "Jane", "Doe"),
                Instant.now(),
                Instant.now()
        );

        UUID currentUserId = UUID.randomUUID();
        when(service.getMyRequests(currentUserId)).thenReturn(List.of(response));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CurrentUser(currentUserId, "jane@example.com", UserRole.USER),
                        null
                )
        );

        mockMvc.perform(get("/api/requests/my-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Need transport"));
    }

    @Test
    void createShouldValidateRequiredFields() throws Exception {
        CreateHelpRequestRequest request = new CreateHelpRequestRequest(
                "",
                "",
                null,
                "Miami"
        );

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("validation_error"));
    }
}
