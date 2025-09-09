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
@Tag(name = "player", description = "API to manage player data")
@RequiredArgsConstructor
public class UpdatePlayerByIdController {

    private final UpdatePlayerByIdService updatePlayerByIdService;

    @PatchMapping(value = "/{id}/name")
    @Operation(summary = "Updates a player's name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    public Mono<ResponseEntity<Player>> updatePlayerName(@PathVariable Long id, @RequestBody String name) {
        return updatePlayerByIdService.updatePlayerName(id, name)
                .map(player -> ResponseEntity.status(HttpStatus.OK).body(player));
    }

    @PatchMapping(value = "/{id}/balance")
    @Operation(summary = "Updates a player's balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    public Mono<ResponseEntity<Player>> updatePlayerBalance(@PathVariable Long id, @RequestBody Long balance) {
        return updatePlayerByIdService.updatePlayerBalance(id, balance)
                .map(player -> ResponseEntity.status(HttpStatus.OK).body(player));
    }
}
