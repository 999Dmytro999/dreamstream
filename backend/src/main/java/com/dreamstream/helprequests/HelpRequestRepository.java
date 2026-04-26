package com.dreamstream.helprequests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HelpRequestRepository extends JpaRepository<HelpRequestEntity, UUID> {
}
