package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.entities.logic.BlackjackService;
import cat.itacademy.blackjack_api.entities.player.FindPlayerByIdService;
import cat.itacademy.blackjack_api.entities.player.Player;
import cat.itacademy.blackjack_api.entities.player.UpdatePlayerByIdService;
import cat.itacademy.blackjack_api.exception.EmptyFieldException;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateGameService {

    private final GameRepository gameRepository;
    private final FindPlayerByIdService findPlayerByIdService;
    private final BlackjackService blackjackService;
    private final UpdatePlayerByIdService updatePlayerByIdService;

    public Mono<Game> execute(CreateGameDto createGameDto) {
        return validateInitialBetAmount(createGameDto)
                .flatMap(player -> {
                    long newBalance = player.getBalance() - createGameDto.betAmount();
                    return updatePlayerByIdService.updatePlayerBalance(player.getId(), newBalance);
                })
                .then(Mono.defer(() -> blackjackService.startNewGame(createGameDto.playerId(), createGameDto.betAmount(), createGameDto.numberDecks())))
                .flatMap(game -> {
                    game.setCreatedAt(LocalDateTime.now());
                    return gameRepository.save(game);
                });
    }

    private Mono<Player> validateInitialBetAmount(CreateGameDto createGameDto) {
        if (createGameDto.betAmount() <= 0) {
            return Mono.error(new EmptyFieldException("Bet amount must be a positive number."));
        }

        return findPlayerByIdService.execute(createGameDto.playerId())
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + createGameDto.playerId())))
                .flatMap(player -> {
                    if (player.getBalance() < createGameDto.betAmount()) {
                        return Mono.error(new EmptyFieldException("Insufficient balance for the bet."));
                    }
                    return Mono.just(player);
                });
    }
}
