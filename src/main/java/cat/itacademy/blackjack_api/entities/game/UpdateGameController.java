package cat.itacademy.blackjack_api.entities.game;

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
@RequestMapping(value = "/game")
@Tag(name = "game", description = "API to manage the update of Blackjack game")
@RequiredArgsConstructor
public class UpdateGameController {

    private final UpdateGameService updateGameService;

    @PostMapping("/{gameId}")
    @Operation(summary = "Performs an action (hit or stand) on a game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public Mono<ResponseEntity<Game>> playerAction(
            @PathVariable String gameId,
            @RequestBody GameActionDto actionDto
    ) {
        return updateGameService.execute(gameId, actionDto)
                .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
    }
}