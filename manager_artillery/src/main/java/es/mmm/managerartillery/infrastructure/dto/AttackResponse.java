package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AttackResponse(
        @JsonProperty("target") TargetResponse targetResponse,
        @JsonProperty("casualties") Integer casualties,
        @JsonProperty("generation") Integer generation
) { }
