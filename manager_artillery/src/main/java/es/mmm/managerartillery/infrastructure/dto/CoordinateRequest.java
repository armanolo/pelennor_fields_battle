package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoordinateRequest(
        @JsonProperty("x") int x,
        @JsonProperty("y") int y
) { }
