package es.mmm.managerartillery.infrastructure.secondary.service;

import es.mmm.managerartillery.infrastructure.secondary.CannonRepository;
import es.mmm.managerartillery.infrastructure.secondary.dto.Cannon;
import es.mmm.managerartillery.infrastructure.secondary.dto.CannonStatusResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAvailableIonCannonTest {

    private static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(
                        Map.of(
                            "1","http://localhost:3001",
                            "2","http://localhost:3002",
                            "3","http://localhost:3003"
                        ),
                        Map.of(
                            "http://localhost:3001/status", new CannonStatusResponse(1, false),
                            "http://localhost:3002/status", new CannonStatusResponse(2, true),
                            "http://localhost:3003/status", new CannonStatusResponse(3, true)
                        ),
                        Map.of(
                            1, new Cannon(false,"http://localhost:3001"),
                            2, new Cannon(true,"http://localhost:3002"),
                            3, new Cannon(true,"http://localhost:3003")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    void callMyService(
            Map<String, String> cannonServers,
            Map<String, CannonStatusResponse> responseByUrl,
            Map<Integer, Cannon> expected
    ) {
        CannonRepository cannonRepository = mock(CannonRepository.class);
        WebClient webClient = mock(WebClient.class);

        var uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(uriSpecMock);
        responseByUrl.entrySet().stream().forEach( item ->{
            var requestHeadersMock = mock(WebClient.RequestHeadersSpec.class);
            var responseSpecMock = mock(WebClient.ResponseSpec.class);

            when(uriSpecMock.uri(item.getKey())).thenReturn(requestHeadersMock);
            when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);
            when(responseSpecMock.bodyToMono(CannonStatusResponse.class)).thenReturn(Mono.just(item.getValue()));
        });

        SetAvailableIonCannon ionCannon = new SetAvailableIonCannon(cannonRepository, webClient);
        ionCannon.setCannonServers(cannonServers);

        // Act
        ionCannon.callScheduledService();

        //verify(cannonRepository.putData(any()),times(1)).
        verify(cannonRepository, times(1)).putData(expected);
    }
}
