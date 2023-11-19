package es.mmm.managerartillery.application.dto;

import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.CoordinateRequest;
import es.mmm.managerartillery.infrastructure.dto.EnemiesRequest;
import es.mmm.managerartillery.infrastructure.dto.ScanRequest;

import java.util.List;

public class AttackRequestMother {

    public static AttackRequest valid(){
        return new AttackRequest(
                List.of("closest-enemies"),
                List.of(
                    new ScanRequest(
                        new CoordinateRequest(5,5),
                        new EnemiesRequest("soldier", 10),
                        null
                    ),
                    new ScanRequest(
                        new CoordinateRequest(29,20),
                        new EnemiesRequest("soldier", 40),
                        5
                    )
                )
        );
    }
}
