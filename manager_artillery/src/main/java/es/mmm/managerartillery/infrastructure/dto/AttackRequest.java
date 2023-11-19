package es.mmm.managerartillery.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AttackRequest(
        @JsonProperty("protocols") List<String> protocols,
        @JsonProperty("scan") List<ScanRequest> scan ) { }
