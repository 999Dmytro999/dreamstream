package com.dreamstream.helprequests;

import com.dreamstream.common.exception.ResourceNotFoundException;
import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import com.dreamstream.helprequests.mapper.HelpRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpRequestServiceTest {

    @Mock
    private HelpRequestRepository repository;

    private HelpRequestMapper mapper;

    @InjectMocks
    private HelpRequestService service;

    @BeforeEach
    void setUp() {
        mapper = new HelpRequestMapper();
        service = new HelpRequestService(repository, mapper);
    }

    @Test
    void createShouldPersistAndReturnResponse() {
        CreateHelpRequestRequest request = new CreateHelpRequestRequest(
                "Need groceries",
                "Need help buying groceries this evening",
                HelpRequestCategory.FOOD,
                "Austin"
        );

        UUID ownerId = UUID.randomUUID();

        HelpRequestEntity saved = new HelpRequestEntity();
        saved.setId(UUID.randomUUID());
        saved.setTitle(request.title());
        saved.setDescription(request.description());
        saved.setCategory(request.category());
        saved.setLocation(request.location());
        saved.setStatus(HelpRequestStatus.OPEN);
        saved.setOwnerId(ownerId);

        when(repository.save(any(HelpRequestEntity.class))).thenReturn(saved);

        var response = service.create(request, ownerId);

        assertEquals(saved.getId(), response.id());
        assertEquals("Need groceries", response.title());
        assertEquals(HelpRequestCategory.FOOD, response.category());
        verify(repository).save(any(HelpRequestEntity.class));
    }

    @Test
    void updateShouldThrowWhenRequestNotFound() {
        UUID id = UUID.randomUUID();
        UpdateHelpRequestRequest request = new UpdateHelpRequestRequest(
                "Updated title",
                "Updated description",
                HelpRequestCategory.OTHER,
                "Remote",
                HelpRequestStatus.IN_PROGRESS
        );

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, request));
    }
}
