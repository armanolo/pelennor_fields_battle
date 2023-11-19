package es.mmm.managerartillery.infrastructure.secondary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CannonStatusResponse(
        @JsonProperty("generation") int generation,
        @JsonProperty("available") boolean available
) { }
