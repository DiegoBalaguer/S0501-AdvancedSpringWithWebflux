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
@Tag(name = "player", description = "API to manage Blackjack game players.")
public class GetRankingController {

    private final GetRankingService GET_RANKING_SERVICE;

    public GetRankingController(GetRankingService getRankingService) {
        this.GET_RANKING_SERVICE = getRankingService;
    }

    @GetMapping("/ranking")
    @Operation(summary = "Gets the player ranking", description = "Returns a list of players sorted by score in descending order.")
    @ApiResponse(responseCode = "200", description = "OK")
    public Flux<GetPlayerRankingDto> getPlayersRanking() {
        return GET_RANKING_SERVICE.execute();
    }
}
