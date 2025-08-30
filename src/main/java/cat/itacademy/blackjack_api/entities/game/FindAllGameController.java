package cat.itacademy.blackjack_api.entities.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
    @RequestMapping("/game")
    @Tag(name = "game", description = "API to obtain the list of games in the Blackjack game.")
    public class FindAllGameController {

        private final FindAllGameService FIND_ALL_GAME_SERVICE;

        public FindAllGameController(FindAllGameService findAllGameService) {
            this.FIND_ALL_GAME_SERVICE = findAllGameService;
        }

        @Operation(summary = "Get a list games", description = "Recover a list games.")
        @ApiResponse(responseCode = "200", description = "OK")
        @ApiResponse(responseCode = "404", description = "NOT FOUND")
        @GetMapping("/all")
        public Flux<Game> getAllGame() {
            return FIND_ALL_GAME_SERVICE.execute();
        }
    }

