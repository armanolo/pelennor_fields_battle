package es.mmm.managerartillery.application.dto;

import es.mmm.managerartillery.domain.valueobject.CannonResponse;

public class CannonResponseMother {
    public static CannonResponse valid(){
        return new CannonResponse(1,2,3,4);
    }
}
