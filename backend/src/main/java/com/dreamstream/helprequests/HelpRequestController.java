package com.dreamstream.helprequests;

import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/requests")
public class HelpRequestController {

    private final HelpRequestService helpRequestService;

    public HelpRequestController(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }

    @GetMapping
    public List<HelpRequestResponse> getAll() {
        return helpRequestService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HelpRequestResponse create(
            @RequestHeader("X-User-Id") UUID currentUserId,
            @Valid @RequestBody CreateHelpRequestRequest request
    ) {
        return helpRequestService.create(request, currentUserId);
    }

    @GetMapping("/{id}")
    public HelpRequestResponse getById(@PathVariable UUID id) {
        return helpRequestService.getById(id);
    }

    @PutMapping("/{id}")
    public HelpRequestResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateHelpRequestRequest request) {
        return helpRequestService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        helpRequestService.delete(id);
    }
}
