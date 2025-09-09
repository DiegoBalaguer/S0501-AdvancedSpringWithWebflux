package cat.itacademy.blackjack_api.entities.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@Tag(name = "game", description = "API to obtain a game by his ID.")
@RequiredArgsConstructor
public class FindGameByIdController {

    private final FindGameByIdService findGameByIdService;

    @Operation(summary = "Get a game by his id", description = "Recover a game by his ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/{id}")
    public Mono<Game> getGameById(
            @Parameter(description = "Game Id", required = true)
            @PathVariable String id) {
        return findGameByIdService.execute(id);
    }
}
