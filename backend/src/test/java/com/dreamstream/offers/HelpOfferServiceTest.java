package com.dreamstream.offers;

import com.dreamstream.common.exception.ForbiddenOperationException;
import com.dreamstream.common.exception.ResourceNotFoundException;
import com.dreamstream.helprequests.HelpRequestEntity;
import com.dreamstream.helprequests.HelpRequestRepository;
import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.mapper.HelpOfferMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class HelpOfferServiceTest {

    @Mock
    private HelpOfferRepository helpOfferRepository;

    @Mock
    private HelpRequestRepository helpRequestRepository;

    private HelpOfferService helpOfferService;

    @BeforeEach
    void setUp() {
        helpOfferService = new HelpOfferService(helpOfferRepository, helpRequestRepository, new HelpOfferMapper());
    }

    @Test
    void createOfferShouldPersistAndReturnResponse() {
        UUID requestId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID currentUserId = UUID.randomUUID();

        HelpRequestEntity helpRequest = new HelpRequestEntity();
        helpRequest.setId(requestId);
        helpRequest.setOwnerId(ownerId);

        HelpOfferEntity savedOffer = new HelpOfferEntity();
        savedOffer.setId(UUID.randomUUID());
        savedOffer.setHelpRequest(helpRequest);
        savedOffer.setOfferedBy(currentUserId);
        savedOffer.setMessage("I can help on Tuesday");
        savedOffer.setStatus(HelpOfferStatus.PENDING);

        when(helpRequestRepository.findById(requestId)).thenReturn(Optional.of(helpRequest));
        when(helpOfferRepository.save(any(HelpOfferEntity.class))).thenReturn(savedOffer);

        var response = helpOfferService.createOffer(requestId, currentUserId, new CreateHelpOfferRequest("I can help on Tuesday"));

        assertEquals(savedOffer.getId(), response.id());
        assertEquals(requestId, response.helpRequestId());
        assertEquals(currentUserId, response.offeredBy());
        verify(helpOfferRepository).save(any(HelpOfferEntity.class));
    }

    @Test
    void acceptOfferShouldFailForNonOwner() {
        UUID offerId = UUID.randomUUID();

        HelpRequestEntity helpRequest = new HelpRequestEntity();
        helpRequest.setId(UUID.randomUUID());
        helpRequest.setOwnerId(UUID.randomUUID());

        HelpOfferEntity offer = new HelpOfferEntity();
        offer.setId(offerId);
        offer.setHelpRequest(helpRequest);
        offer.setOfferedBy(UUID.randomUUID());
        offer.setStatus(HelpOfferStatus.PENDING);

        when(helpOfferRepository.findById(offerId)).thenReturn(Optional.of(offer));

        assertThrows(ForbiddenOperationException.class, () -> helpOfferService.acceptOffer(offerId, UUID.randomUUID()));
    }

    @Test
    void cancelOfferShouldFailWhenOfferNotFound() {
        UUID offerId = UUID.randomUUID();
        when(helpOfferRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> helpOfferService.cancelOffer(offerId, UUID.randomUUID()));
    }
}
