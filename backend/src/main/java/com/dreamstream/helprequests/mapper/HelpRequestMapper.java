package com.dreamstream.helprequests.mapper;

import com.dreamstream.helprequests.HelpRequestEntity;
import com.dreamstream.helprequests.HelpRequestStatus;
import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import org.springframework.stereotype.Component;

@Component
public class HelpRequestMapper {

    public HelpRequestEntity toEntity(CreateHelpRequestRequest request) {
        HelpRequestEntity entity = new HelpRequestEntity();
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setLocation(request.location());
        entity.setStatus(HelpRequestStatus.OPEN);
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
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
