package com.dreamstream.offers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HelpOfferRepository extends JpaRepository<HelpOfferEntity, UUID> {

    List<HelpOfferEntity> findByOfferedByOrderByCreatedAtDesc(UUID offeredBy);

    List<HelpOfferEntity> findByHelpRequest_IdOrderByCreatedAtDesc(UUID helpRequestId);
}
