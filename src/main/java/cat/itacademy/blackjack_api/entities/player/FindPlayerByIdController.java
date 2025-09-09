package cat.itacademy.blackjack_api.entities.player;

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
@RequestMapping("/player")
@Tag(name = "player", description = "API to obtain the list of players in the Blackjack game.")
@RequiredArgsConstructor
public class FindPlayerByIdController {

    private final FindPlayerByIdService findPlayerByIdService;

    @Operation(summary = "Get a player by his id", description = "Recover a player by his ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/{id}")
    public Mono<Player> getPlayerById(
            @Parameter(description = "Player Id", required = true)
            @PathVariable Long id) {
        return findPlayerByIdService.execute(id);
    }
}
