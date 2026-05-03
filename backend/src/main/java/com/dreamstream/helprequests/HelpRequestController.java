package com.dreamstream.helprequests;

import com.dreamstream.helprequests.dto.CreateHelpRequestRequest;
import com.dreamstream.helprequests.dto.HelpRequestResponse;
import com.dreamstream.helprequests.dto.UpdateHelpRequestRequest;
import com.dreamstream.security.CurrentUser;
import com.dreamstream.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<HelpRequestResponse> getAll(@RequestParam(required = false) HelpRequestStatus status) {
        return helpRequestService.getAll(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HelpRequestResponse create(@Valid @RequestBody CreateHelpRequestRequest request) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpRequestService.create(request, currentUser.id());
    }

    @GetMapping("/{id}")
    public HelpRequestResponse getById(@PathVariable UUID id) {
        return helpRequestService.getById(id);
    }

    @PutMapping("/{id}")
    public HelpRequestResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateHelpRequestRequest request) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        return helpRequestService.update(id, currentUser.id(), request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        CurrentUser currentUser = SecurityUtils.requireCurrentUser();
        helpRequestService.delete(id, currentUser.id());
    }
}
