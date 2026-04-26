package com.dreamstream.offers;

import com.dreamstream.offers.dto.CreateHelpOfferRequest;
import com.dreamstream.offers.dto.HelpOfferResponse;
import com.dreamstream.security.CurrentUser;
import com.dreamstream.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @Valid @RequestBody CreateHelpOfferRequest request
    ) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpOfferService.createOffer(requestId, currentUser.id(), request);
    }

    @GetMapping("/my-offers")
    public List<HelpOfferResponse> getMyOffers() {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpOfferService.getMyOffers(currentUser.id());
    }

    @GetMapping("/requests/{requestId}/offers")
    public List<HelpOfferResponse> getOffersForRequest(@PathVariable UUID requestId) {
        return helpOfferService.getOffersForRequest(requestId);
    }

    @PutMapping("/offers/{offerId}/accept")
    public HelpOfferResponse acceptOffer(
            @PathVariable UUID offerId
    ) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpOfferService.acceptOffer(offerId, currentUser.id());
    }

    @PutMapping("/offers/{offerId}/decline")
    public HelpOfferResponse declineOffer(
            @PathVariable UUID offerId
    ) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpOfferService.declineOffer(offerId, currentUser.id());
    }

    @PutMapping("/offers/{offerId}/cancel")
    public HelpOfferResponse cancelOffer(
            @PathVariable UUID offerId
    ) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpOfferService.cancelOffer(offerId, currentUser.id());
    }
}
