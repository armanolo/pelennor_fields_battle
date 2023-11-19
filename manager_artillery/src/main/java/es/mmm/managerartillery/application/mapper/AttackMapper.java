package es.mmm.managerartillery.application.mapper;


import es.mmm.managerartillery.domain.Attack;
import es.mmm.managerartillery.domain.Target;
import es.mmm.managerartillery.domain.valueobject.Allies;
import es.mmm.managerartillery.domain.valueobject.Coordinates;
import es.mmm.managerartillery.domain.valueobject.Enemies;
import es.mmm.managerartillery.domain.valueobject.ProtocolType;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;

import java.util.List;
import java.util.Optional;

public class AttackMapper {

    public Attack dtoToEntity(AttackRequest attackRequest){
        List<ProtocolType> protocolTypeList = attackRequest.protocols().stream().map( ProtocolType::fromValue).toList();


        //scan = {ArrayList@3210}  size = 2
        //0 = {ScanRequest@3212} "ScanRequest[coordinates=CoordinateRequest[x=0, y=40], enemies=EnemiesRequest[type=soldier, number=10], allies=null]"

        List<Target> targetList = attackRequest.scan().stream().map(
                targetRequest -> {

                    Coordinates coordinates = new Coordinates(
                            targetRequest.coordinates().x(), targetRequest.coordinates().y()
                    );

                    Enemies enemies = new Enemies(targetRequest.enemies().type(), targetRequest.enemies().number());

                    Allies allies = null;

                    if(targetRequest.allies() != null){
                        allies = new Allies(targetRequest.allies());
                    }

                    return new Target(coordinates, enemies, Optional.ofNullable(allies));
                }
        ).toList();


        return new Attack(protocolTypeList, targetList);
    }

}
