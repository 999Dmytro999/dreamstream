package com.dreamstream.offers.mapper;

import com.dreamstream.helprequests.HelpRequestEntity;
import com.dreamstream.offers.HelpOfferEntity;
import com.dreamstream.offers.HelpOfferStatus;
import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.dto.HelpOfferResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelpOfferMapper {

    public HelpOfferEntity toEntity(CreateHelpOfferRequest request, HelpRequestEntity helpRequest, UUID offeredBy) {
        HelpOfferEntity entity = new HelpOfferEntity();
        entity.setHelpRequest(helpRequest);
        entity.setOfferedBy(offeredBy);
        entity.setMessage(request.message());
        entity.setStatus(HelpOfferStatus.PENDING);
        return entity;
    }

    public HelpOfferResponse toResponse(HelpOfferEntity entity) {
        return new HelpOfferResponse(
                entity.getId(),
                entity.getHelpRequest().getId(),
                entity.getOfferedBy(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
