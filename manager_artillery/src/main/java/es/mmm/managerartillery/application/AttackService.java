package es.mmm.managerartillery.application;

import es.mmm.managerartillery.application.mapper.AttackMapper;
import es.mmm.managerartillery.domain.Attack;
import es.mmm.managerartillery.domain.service.FireService;
import es.mmm.managerartillery.domain.valueobject.CannonResponse;
import es.mmm.managerartillery.domain.valueobject.Coordinates;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import es.mmm.managerartillery.infrastructure.dto.TargetResponse;
import org.springframework.stereotype.Service;

@Service
public class AttackService {
    private final FireService fireService;
    private final AttackMapper attackMapper = new AttackMapper();

    public AttackService(FireService fireService) {
        this.fireService = fireService;
    }

    public AttackResponse execute(AttackRequest attackRequest){
        Attack attack = attackMapper.dtoToEntity(attackRequest);

        CannonResponse cannonResponse = fireService.execute(attack);
        Coordinates coordinates = cannonResponse.getTarget();
        int enemies = cannonResponse.getEnemies();

        return new AttackResponse(
                new TargetResponse( coordinates.getX(), coordinates.getY()),
                enemies,
                cannonResponse.getGeneration()
        );
    }
}
