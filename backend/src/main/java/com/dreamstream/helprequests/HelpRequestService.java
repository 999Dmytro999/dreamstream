package com.dreamstream.helprequests;

import com.dreamstream.common.exception.ResourceNotFoundException;
import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import com.dreamstream.helprequests.mapper.HelpRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HelpRequestService {

    private final HelpRequestRepository repository;
    private final HelpRequestMapper mapper;

    public HelpRequestService(HelpRequestRepository repository, HelpRequestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<HelpRequestResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public HelpRequestResponse create(CreateHelpRequestRequest request) {
        HelpRequestEntity saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HelpRequestResponse getById(UUID id) {
        return mapper.toResponse(getEntityById(id));
    }

    @Transactional
    public HelpRequestResponse update(UUID id, UpdateHelpRequestRequest request) {
        HelpRequestEntity entity = getEntityById(id);
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        HelpRequestEntity entity = getEntityById(id);
        repository.delete(entity);
    }

    private HelpRequestEntity getEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + id));
    }
}
