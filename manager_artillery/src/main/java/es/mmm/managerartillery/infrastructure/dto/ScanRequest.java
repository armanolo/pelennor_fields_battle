package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ScanRequest(
        @JsonProperty("coordinates") CoordinateRequest coordinates,
        @JsonProperty("enemies") EnemiesRequest enemies,
        @JsonProperty("allies") Integer allies
) { }
