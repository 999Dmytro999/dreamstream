package com.dreamstream.offers;

import com.dreamstream.common.exception.ConflictException;
import com.dreamstream.common.exception.ForbiddenOperationException;
import com.dreamstream.common.exception.ResourceNotFoundException;
import com.dreamstream.helprequests.HelpRequestEntity;
import com.dreamstream.helprequests.HelpRequestRepository;
import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.dto.HelpOfferResponse;
import com.dreamstream.offers.mapper.HelpOfferMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HelpOfferService {

    private final HelpOfferRepository helpOfferRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final HelpOfferMapper helpOfferMapper;

    public HelpOfferService(
            HelpOfferRepository helpOfferRepository,
            HelpRequestRepository helpRequestRepository,
            HelpOfferMapper helpOfferMapper
    ) {
        this.helpOfferRepository = helpOfferRepository;
        this.helpRequestRepository = helpRequestRepository;
        this.helpOfferMapper = helpOfferMapper;
    }

    @Transactional
    public HelpOfferResponse createOffer(UUID requestId, UUID currentUserId, CreateHelpOfferRequest request) {
        HelpRequestEntity helpRequest = getHelpRequestById(requestId);

        if (helpRequest.getOwnerId().equals(currentUserId)) {
            throw new ConflictException("Request owner cannot create an offer on their own request");
        }

        HelpOfferEntity saved = helpOfferRepository.save(helpOfferMapper.toEntity(request, helpRequest, currentUserId));
        return helpOfferMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<HelpOfferResponse> getMyOffers(UUID currentUserId) {
        return helpOfferRepository.findByOfferedByOrderByCreatedAtDesc(currentUserId).stream()
                .map(helpOfferMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HelpOfferResponse> getOffersForRequest(UUID requestId) {
        getHelpRequestById(requestId);
        return helpOfferRepository.findByHelpRequest_IdOrderByCreatedAtDesc(requestId).stream()
                .map(helpOfferMapper::toResponse)
                .toList();
    }

    @Transactional
    public HelpOfferResponse acceptOffer(UUID offerId, UUID currentUserId) {
        HelpOfferEntity offer = getOfferById(offerId);
        ensureRequestOwner(offer, currentUserId);
        updateStatus(offer, HelpOfferStatus.ACCEPTED);
        return helpOfferMapper.toResponse(helpOfferRepository.save(offer));
    }

    @Transactional
    public HelpOfferResponse declineOffer(UUID offerId, UUID currentUserId) {
        HelpOfferEntity offer = getOfferById(offerId);
        ensureRequestOwner(offer, currentUserId);
        updateStatus(offer, HelpOfferStatus.DECLINED);
        return helpOfferMapper.toResponse(helpOfferRepository.save(offer));
    }

    @Transactional
    public HelpOfferResponse cancelOffer(UUID offerId, UUID currentUserId) {
        HelpOfferEntity offer = getOfferById(offerId);
        if (!offer.getOfferedBy().equals(currentUserId)) {
            throw new ForbiddenOperationException("Only the offer creator can cancel this offer");
        }
        updateStatus(offer, HelpOfferStatus.CANCELLED);
        return helpOfferMapper.toResponse(helpOfferRepository.save(offer));
    }

    private void ensureRequestOwner(HelpOfferEntity offer, UUID currentUserId) {
        UUID ownerId = offer.getHelpRequest().getOwnerId();
        if (!ownerId.equals(currentUserId)) {
            throw new ForbiddenOperationException("Only the request owner can perform this action");
        }
    }

    private void updateStatus(HelpOfferEntity offer, HelpOfferStatus nextStatus) {
        if (offer.getStatus() != HelpOfferStatus.PENDING) {
            throw new ConflictException("Only pending offers can change status");
        }
        offer.setStatus(nextStatus);
    }

    private HelpRequestEntity getHelpRequestById(UUID requestId) {
        return helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + requestId));
    }

    private HelpOfferEntity getOfferById(UUID offerId) {
        return helpOfferRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Help offer not found: " + offerId));
    }
}
