package es.mmm.managerartillery.infrastructure.primary;

import es.mmm.managerartillery.application.AttackService;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@Tag(name = "Mowers operation")
@RequestMapping("/attack")
public class AttackController {
    private final AttackService attackService;

    @Autowired
    public AttackController(AttackService attackService) {
        this.attackService = attackService;
    }

    @Operation(summary = "Send attack")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "success operation",
                    content = @Content(
                        mediaType = "application/json",schema = @Schema(implementation = AttackRequest.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = AttackResponse.class)
                    )
            ),
    })
    @PostMapping
    public ResponseEntity<AttackResponse> execute(@RequestBody AttackRequest attackRequest){
        AttackResponse attackResponse = attackService.execute(attackRequest);
        return new ResponseEntity<>(attackResponse, HttpStatus.OK);
    }
}
