package es.mmm.managerartillery.domain.service;

import es.mmm.managerartillery.domain.Attack;
import es.mmm.managerartillery.domain.exception.CannonNotAvailableException;
import es.mmm.managerartillery.domain.repository.FireAction;
import es.mmm.managerartillery.domain.repository.GetCannonAvailable;
import es.mmm.managerartillery.domain.valueobject.CannonAvailable;
import es.mmm.managerartillery.domain.valueobject.CannonResponse;


public class FireService {
    
    private final GetCannonAvailable getCannonAvailable;

    private final FireAction fireAction;
    
    public FireService(GetCannonAvailable getCannon, FireAction fireAction){
        this.getCannonAvailable = getCannon;
        this.fireAction = fireAction;
    }
    
    public CannonResponse execute(Attack attack){

        CannonAvailable cannonAvailable = getCannonAvailable.getAvailableCannon();

        if(cannonAvailable == null){
            throw new CannonNotAvailableException("There not cannon available");
        }

        return fireAction.execute(
                cannonAvailable,
                attack.getTargetToFire()
        );
    }
}
