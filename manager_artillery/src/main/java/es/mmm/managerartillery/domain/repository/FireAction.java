package es.mmm.managerartillery.domain.repository;

import es.mmm.managerartillery.domain.Target;
import es.mmm.managerartillery.domain.valueobject.CannonAvailable;
import es.mmm.managerartillery.domain.valueobject.CannonResponse;

public interface FireAction {
    CannonResponse execute(CannonAvailable execute, Target targetToFire);
}
