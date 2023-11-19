package es.mmm.managerartillery.infrastructure.secondary.service;

import es.mmm.managerartillery.infrastructure.secondary.CannonRepository;
import es.mmm.managerartillery.infrastructure.secondary.dto.Cannon;
import es.mmm.managerartillery.infrastructure.secondary.dto.CannonStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;

@Service
@ConfigurationProperties(prefix = "custom")
public class SetAvailableIonCannon {
    private Map<String, String> cannonServers;
    private final WebClient webClient;
    private final CannonRepository cannonRepository;

    private static final Logger logger = LoggerFactory.getLogger(SetAvailableIonCannon.class);


    public SetAvailableIonCannon(CannonRepository cannonRepository, WebClient webClient) {
        this.cannonRepository = cannonRepository;
        this.webClient = webClient;
    }

    @Scheduled(fixedRate = 1000) // Execute every 1 second
    public void callScheduledService() {
        Mono<TreeMap<Integer, Cannon>> treeMapMono = random();
        treeMapMono.subscribe(
                cannonRepository::putData,
                error -> logger.error("Error: {}",error.getMessage()),
                () -> { }
        );
    }

    public void setCannonServers(Map<String, String> cannonServers) {
        this.cannonServers = cannonServers;
    }


    private Mono<CannonStatusResponse> getCannonStatus(int id, String uriCannon) {
        return webClient.get().uri(uriCannon+"/status").retrieve()
                .bodyToMono(CannonStatusResponse.class)
                .retryWhen(Retry.backoff (3, Duration.ofSeconds(1)))
                .onErrorResume(ex -> Mono.just(new CannonStatusResponse(id, false)));
    }

    private Cannon convertFromT(CannonStatusResponse cannonStatusResponse){

        return new Cannon(
                cannonStatusResponse.available(),
                cannonServers.get(String.valueOf(cannonStatusResponse.generation()))
        );
    }

    private Mono<TreeMap<Integer,Cannon>> random() {
        return Mono.zip(
                getCannonStatus(1,cannonServers.get("1")),
                getCannonStatus(2,cannonServers.get("2")),
                getCannonStatus(3,cannonServers.get("3"))
        ).map(
                t -> {
                    TreeMap<Integer, Cannon> treeMap = new TreeMap<>();
                    treeMap.put(t.getT1().generation(), convertFromT(t.getT1()));
                    treeMap.put(t.getT2().generation(), convertFromT(t.getT2()));
                    treeMap.put(t.getT3().generation(), convertFromT(t.getT3()));
                    return treeMap;
                }
        );
    }

}
