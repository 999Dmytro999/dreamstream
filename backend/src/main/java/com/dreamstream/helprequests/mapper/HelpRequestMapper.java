package com.dreamstream.helprequests.mapper;

import com.dreamstream.helprequests.HelpRequestEntity;
import com.dreamstream.helprequests.HelpRequestStatus;
import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestCreatedByResponse;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import com.dreamstream.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class HelpRequestMapper {

    public HelpRequestEntity toEntity(CreateHelpRequestRequest request, UserEntity createdBy) {
        HelpRequestEntity entity = new HelpRequestEntity();
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setLocation(request.location());
        entity.setStatus(HelpRequestStatus.OPEN);
        entity.setCreatedBy(createdBy);
        return entity;
    }

    public void updateEntity(HelpRequestEntity entity, UpdateHelpRequestRequest request) {
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setLocation(request.location());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
    }

    public HelpRequestResponse toResponse(HelpRequestEntity entity) {
        return new HelpRequestResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getLocation(),
                entity.getStatus(),
                new HelpRequestCreatedByResponse(
                        entity.getCreatedBy().getId(),
                        entity.getCreatedBy().getFirstName(),
                        entity.getCreatedBy().getLastName()
                ),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
