package com.dreamstream.helprequests;

import com.dreamstream.common.exception.ForbiddenOperationException;
import com.dreamstream.common.exception.ResourceNotFoundException;
import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import com.dreamstream.helprequests.mapper.HelpRequestMapper;
import com.dreamstream.users.UserEntity;
import com.dreamstream.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HelpRequestService {

    private final HelpRequestRepository repository;
    private final HelpRequestMapper mapper;
    private final UserRepository userRepository;

    public HelpRequestService(HelpRequestRepository repository, HelpRequestMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<HelpRequestResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public HelpRequestResponse create(CreateHelpRequestRequest request, UUID currentUserId) {
        UserEntity user = getUserById(currentUserId);
        HelpRequestEntity saved = repository.save(mapper.toEntity(request, user));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HelpRequestResponse getById(UUID id) {
        return mapper.toResponse(getEntityById(id));
    }

    @Transactional
    public HelpRequestResponse update(UUID id, UUID currentUserId, UpdateHelpRequestRequest request) {
        HelpRequestEntity entity = getEntityById(id);
        ensureOwner(entity, currentUserId);
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id, UUID currentUserId) {
        HelpRequestEntity entity = getEntityById(id);
        ensureOwner(entity, currentUserId);
        repository.delete(entity);
    }

    private void ensureOwner(HelpRequestEntity entity, UUID currentUserId) {
        if (!entity.getCreatedBy().getId().equals(currentUserId)) {
            throw new ForbiddenOperationException("Only the request owner can modify this request");
        }
    }

    private HelpRequestEntity getEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + id));
    }

    private UserEntity getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }
}
