package es.mmm.managerartillery.infrastructure.primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mmm.managerartillery.application.AttackService;
import es.mmm.managerartillery.domain.service.FireService;
import es.mmm.managerartillery.domain.valueobject.CannonResponse;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AttackControllerTest {

    private AttackService attackService;

    private AttackController attackController;
    private FireService fireService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void execute() throws JsonProcessingException {

        String attackJson = "{\"protocols\":[\"avoid-mumakil\"],\"scan\":[{\"coordinates\":{\"x\":0,\"y\":40},\"enemies\":{\"type\":\"soldier\",\"number\":10}},{\"coordinates\":{\"x\":0,\"y\":80},\"allies\":5,\"enemies\":{\"type\":\"mumakil\",\"number\":1}}]}";
        String responseJson = "{\"target\":{\"x\":0,\"y\":40},\"casualties\":10,\"generation\":1}";

        AttackRequest attackRequest = objectMapper.readValue(attackJson, AttackRequest.class);
        AttackResponse expectedResult = objectMapper.readValue(responseJson, AttackResponse.class);

        fireService = mock(FireService.class);
        when(fireService.execute(any())).thenReturn(new CannonResponse(1,0,40,10));

        attackService = new AttackService(fireService);
        attackController = new AttackController(attackService);
        AttackResponse result = attackController.execute(attackRequest).getBody();

        verify(fireService,times(1)).execute(any());
        assertThat(expectedResult).isEqualTo(result);
    }
}
