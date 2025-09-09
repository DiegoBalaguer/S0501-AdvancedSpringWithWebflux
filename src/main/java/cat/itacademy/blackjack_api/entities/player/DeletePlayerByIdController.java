package cat.itacademy.blackjack_api.entities.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
@Tag(name = "player", description = "API to delete player.")
@RequiredArgsConstructor
public class DeletePlayerByIdController {

    private final DeletePlayerByIdService deletePlayerByIdService;

    @Operation(summary = "Delete player", description = "Delete a player by ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @DeleteMapping("/{id}")
    public Mono<Void> deletePlayerById(
            @Parameter(description = "Player Id", required = true)
            @PathVariable Long id) {
        return deletePlayerByIdService.execute(id);
    }
}
