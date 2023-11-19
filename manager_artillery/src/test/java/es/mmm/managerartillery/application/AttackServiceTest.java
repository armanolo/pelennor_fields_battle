package es.mmm.managerartillery.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mmm.managerartillery.application.dto.AttackRequestMother;
import es.mmm.managerartillery.application.dto.CannonResponseMother;
import es.mmm.managerartillery.domain.exception.InvalidEnemyTypeException;
import es.mmm.managerartillery.domain.exception.InvalidProtocolTypeException;
import es.mmm.managerartillery.domain.exception.InvalidTargetCoordinateException;
import es.mmm.managerartillery.domain.service.FireService;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AttackServiceTest {
    private AttackService attackService;
    private FireService fireService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        fireService = mock(FireService.class);
        attackService = new AttackService(fireService);
    }

    @DisplayName("Execute a valid service attack")
    @Test
    void executeValidAttack() {
        //Given
        AttackRequest attackRequest = AttackRequestMother.valid();
        when(fireService.execute(any())).thenReturn(CannonResponseMother.valid());

        //When
        AttackResponse response = attackService.execute(attackRequest);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.generation()).isSameAs(1);
        assertThat(response.targetResponse().x()).isSameAs(2);
        assertThat(response.targetResponse().y()).isSameAs(3);
        assertThat(response.casualties()).isSameAs(4);
    }

    @DisplayName("Should return an exception by invalid data request by invalid coordinates")
    @Test
    void shouldReturnAnExceptionByInvalidCoordinates() throws JsonProcessingException {
        //Given
        String jsonAttack = "{\"protocols\":[\"avoid-mumakil\"]," +
                "\"scan\":[{\"coordinates\":{\"x\":-1,\"y\":40},\"enemies\":{\"type\":\"soldier\",\"number\":10}}]}";
        AttackRequest attackRequest = objectMapper.readValue(jsonAttack, AttackRequest.class);

        assertThatThrownBy(
                () -> attackService.execute(attackRequest)
        ).isInstanceOf(InvalidTargetCoordinateException.class)
                .hasMessage("Coordinates must be non-negative");
    }

    @DisplayName("Should return an exception by invalid data request by invalid protocol")
    @Test
    void shouldReturnAnExceptionByInvalidProtocol() throws JsonProcessingException {
        //Given
        String jsonAttack = "{\"protocols\":[\"none-protocol\"]," +
                "\"scan\":[{\"coordinates\":{\"x\":1,\"y\":40},\"enemies\":{\"type\":\"soldier\",\"number\":10}}]}";
        AttackRequest attackRequest = objectMapper.readValue(jsonAttack, AttackRequest.class);

        assertThatThrownBy(
                () -> attackService.execute(attackRequest)
        ).isInstanceOf(InvalidProtocolTypeException.class)
                .hasMessage("Invalid protocol value: none-protocol");
    }

    @DisplayName("Should return an exception by invalid data request by invalid enemy")
    @Test
    void shouldReturnAnExceptionByInvalidEnemy() throws JsonProcessingException {
        //Given
        String jsonAttack = "{\"protocols\":[\"avoid-mumakil\"]," +
                "\"scan\":[{\"coordinates\":{\"x\":1,\"y\":40},\"enemies\":{\"type\":\"ewok\",\"number\":10}}]}";
        AttackRequest attackRequest = objectMapper.readValue(jsonAttack, AttackRequest.class);

        assertThatThrownBy(
                () -> attackService.execute(attackRequest)
        ).isInstanceOf(InvalidEnemyTypeException.class)
                .hasMessage("Invalid enemy value: ewok");
    }
}
