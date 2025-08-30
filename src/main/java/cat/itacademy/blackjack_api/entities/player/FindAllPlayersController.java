package cat.itacademy.blackjack_api.entities.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/player")
@Tag(name = "player", description = "API to obtain the list of players in the Blackjack game.")
public class FindAllPlayersController {

    private final FindAllPlayersService FIND_ALL_PLAYERS_SERVICE;

    public FindAllPlayersController(FindAllPlayersService findAllPlayersService) {
        this.FIND_ALL_PLAYERS_SERVICE = findAllPlayersService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "List all players", description = "We get all the players registered in the system.")
    @ApiResponse(responseCode = "200", description = "OK")
    public Flux<Player> getAllPlayers() {
        return FIND_ALL_PLAYERS_SERVICE.execute();
    }
}
