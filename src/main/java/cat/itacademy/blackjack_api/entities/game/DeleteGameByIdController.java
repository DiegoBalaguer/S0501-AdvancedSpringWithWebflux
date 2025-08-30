package cat.itacademy.blackjack_api.entities.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@Tag(name = "game", description = "API to delete game.")
public class DeleteGameByIdController {

    private final DeleteGameByIdService DELETE_GAME_BY_ID_SERVICE;

    public DeleteGameByIdController(DeleteGameByIdService deleteGameByIdService) {
        this.DELETE_GAME_BY_ID_SERVICE = deleteGameByIdService;
    }

    @Operation(summary = "Delete game", description = "Delete a game by ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteGameById(
            @Parameter(description = "Game Id", required = true)
            @PathVariable String id) {
        return DELETE_GAME_BY_ID_SERVICE.execute(id);
    }
}
