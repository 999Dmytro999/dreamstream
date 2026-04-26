package com.dreamstream.helprequests.dto;

import com.dreamstream.helprequests.HelpRequestCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHelpRequestRequest(
        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "description is required")
        String description,

        @NotNull(message = "category is required")
        HelpRequestCategory category,

        String location
) {
}
