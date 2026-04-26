package com.dreamstream.offers;

import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.dto.HelpOfferResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class HelpOfferController {

    private final HelpOfferService helpOfferService;

    public HelpOfferController(HelpOfferService helpOfferService) {
        this.helpOfferService = helpOfferService;
    }

    @PostMapping("/requests/{requestId}/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public HelpOfferResponse createOffer(
            @PathVariable UUID requestId,
            @RequestHeader("X-User-Id") UUID currentUserId,
            @Valid @RequestBody CreateHelpOfferRequest request
    ) {
        return helpOfferService.createOffer(requestId, currentUserId, request);
    }

    @GetMapping("/my-offers")
    public List<HelpOfferResponse> getMyOffers(@RequestHeader("X-User-Id") UUID currentUserId) {
        return helpOfferService.getMyOffers(currentUserId);
    }

    @GetMapping("/requests/{requestId}/offers")
    public List<HelpOfferResponse> getOffersForRequest(@PathVariable UUID requestId) {
        return helpOfferService.getOffersForRequest(requestId);
    }

    @PutMapping("/offers/{offerId}/accept")
    public HelpOfferResponse acceptOffer(
            @PathVariable UUID offerId,
            @RequestHeader("X-User-Id") UUID currentUserId
    ) {
        return helpOfferService.acceptOffer(offerId, currentUserId);
    }

    @PutMapping("/offers/{offerId}/decline")
    public HelpOfferResponse declineOffer(
            @PathVariable UUID offerId,
            @RequestHeader("X-User-Id") UUID currentUserId
    ) {
        return helpOfferService.declineOffer(offerId, currentUserId);
    }

    @PutMapping("/offers/{offerId}/cancel")
    public HelpOfferResponse cancelOffer(
            @PathVariable UUID offerId,
            @RequestHeader("X-User-Id") UUID currentUserId
    ) {
        return helpOfferService.cancelOffer(offerId, currentUserId);
    }
}
