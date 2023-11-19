package es.mmm.managerartillery.infrastructure.secondary;

import es.mmm.managerartillery.domain.Target;
import es.mmm.managerartillery.domain.exception.InvalidCannonResponseException;
import es.mmm.managerartillery.domain.repository.FireAction;
import es.mmm.managerartillery.domain.valueobject.CannonAvailable;
import es.mmm.managerartillery.domain.valueobject.CannonResponse;
import es.mmm.managerartillery.infrastructure.dto.CannonFiredResponse;
import es.mmm.managerartillery.infrastructure.dto.CoordinateRequest;
import es.mmm.managerartillery.infrastructure.dto.FireTargetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FireCannon implements FireAction {

    private final RestTemplate restTemplate;

    public FireCannon(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CannonResponse execute(CannonAvailable cannonAvailable, Target targetToFire) {
        FireTargetRequest fireTargetRequest = new FireTargetRequest(
                new CoordinateRequest(
                        targetToFire.getCoordinates().getX(),
                        targetToFire.getCoordinates().getY()
                ),
                targetToFire.getEnemies().getNumber()
        );

        ResponseEntity<CannonFiredResponse> response
                = restTemplate.postForEntity(cannonAvailable.getUrl()+"/fire", fireTargetRequest, CannonFiredResponse.class);

        CannonFiredResponse cannonFiredResponse = response.getBody();
        if(cannonFiredResponse == null){
            throw new InvalidCannonResponseException("No good response from cannon");
        }

        return new CannonResponse(
                cannonAvailable.getCannonId(),
                fireTargetRequest.coordinate().x(),
                fireTargetRequest.coordinate().y(),
                cannonFiredResponse.casualties()
        );
    }
}
