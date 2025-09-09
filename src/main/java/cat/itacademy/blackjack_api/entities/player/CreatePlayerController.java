package cat.itacademy.blackjack_api.entities.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/player")
@Tag(name = "player", description = "API to manage the creation of Blackjack game players")
@RequiredArgsConstructor
public class CreatePlayerController {

    private final CreatePlayerService createPlayerService;

    @PostMapping("/create")
    @Operation(summary = "Create a new player in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or Email already exists"),
            @ApiResponse(responseCode = "409", description = "Conflict, Email already exists")
    })
    public Mono<ResponseEntity<Player>> addPlayer(
            @RequestBody CreatePlayerDto createPlayerDto
    ) {
        return createPlayerService.execute(createPlayerDto)
                .map(savedPlayer -> ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer));
    }
}
