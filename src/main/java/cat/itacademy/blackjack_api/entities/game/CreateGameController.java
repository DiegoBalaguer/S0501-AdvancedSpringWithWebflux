package cat.itacademy.blackjack_api.entities.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

    @RestController
    @RequestMapping(value = "/game")
    @Tag(name = "game", description = "API to manage the creation of Blackjack game")
    public class CreateGameController {

        private final CreateGameService createGameService;

        public CreateGameController(CreateGameService createGameService) {
            this.createGameService = createGameService;
        }

        @PostMapping("/new")
        @Operation(summary = "Create a new game in the system")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Game created"),
                @ApiResponse(responseCode = "400", description = "Invalid input or Email already exists"),
                @ApiResponse(responseCode = "409", description = "Conflict, Email already exists")
        })
        public Mono<ResponseEntity<Game>> addGame(
                @RequestBody CreateGameDto createGameDto
        ) {
            return createGameService.execute(createGameDto)
                    .map(savedGame -> ResponseEntity.status(HttpStatus.CREATED).body(savedGame));
        }
    }
