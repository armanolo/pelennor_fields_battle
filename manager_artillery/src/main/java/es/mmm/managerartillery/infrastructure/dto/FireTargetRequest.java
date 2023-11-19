package es.mmm.managerartillery.infrastructure.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record FireTargetRequest(
        @JsonProperty("target")
        CoordinateRequest coordinate,
        @JsonProperty("enemies")
        int enemies
) { }
