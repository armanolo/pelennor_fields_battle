package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CannonFiredResponse(
        @JsonProperty("casualties") int casualties,
        @JsonProperty("generation") int generation
) { }
