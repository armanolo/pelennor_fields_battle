package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnemiesRequest(
        @JsonProperty("type") String type,
        @JsonProperty("number") int number
) { }
