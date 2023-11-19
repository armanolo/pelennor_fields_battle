package es.mmm.managerartillery.infrastructure.dto;


public record TargetRequest(CoordinateRequest coordinate, EnemiesRequest enemies, AlliesRequest alliesRequest) { }
