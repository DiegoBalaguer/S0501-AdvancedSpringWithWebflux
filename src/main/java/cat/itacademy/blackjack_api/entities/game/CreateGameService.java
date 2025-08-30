package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.entities.logic.BlackjackService;
import cat.itacademy.blackjack_api.entities.player.FindPlayerByIdService;
import cat.itacademy.blackjack_api.entities.player.Player;
import cat.itacademy.blackjack_api.entities.player.UpdatePlayerByIdService;
import cat.itacademy.blackjack_api.exception.EmptyFieldException;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class CreateGameService {

    private final GameRepository GAME_REPOSITORY;
    private final FindPlayerByIdService FIND_PLAYER_BY_ID_SERVICE;
    private final BlackjackService BLACKJACK_SERVICE;
    private final UpdatePlayerByIdService UPDATE_PLAYER_BY_ID_SERVICE;

    public CreateGameService(GameRepository gameRepository, FindPlayerByIdService findPlayerByIdService, BlackjackService blackjackService, UpdatePlayerByIdService updatePlayerByIdService) {
        this.GAME_REPOSITORY = gameRepository;
        this.FIND_PLAYER_BY_ID_SERVICE = findPlayerByIdService;
        this.BLACKJACK_SERVICE = blackjackService;
        this.UPDATE_PLAYER_BY_ID_SERVICE = updatePlayerByIdService;
    }

    public Mono<Game> execute(CreateGameDto createGameDto) {
        return validateInitialBetAmount(createGameDto)
                .flatMap(player -> {
                    long newBalance = player.getBalance() - createGameDto.betAmount();
                    return UPDATE_PLAYER_BY_ID_SERVICE.updatePlayerBalance(player.getId(), newBalance);
                })
                .then(Mono.defer(() -> BLACKJACK_SERVICE.startNewGame(createGameDto.playerId(), createGameDto.betAmount(), createGameDto.numberDecks())))
                .flatMap(game -> {
                    game.setCreatedAt(LocalDateTime.now());
                    return GAME_REPOSITORY.save(game);
                });
    }

    private Mono<Player> validateInitialBetAmount(CreateGameDto createGameDto) {
        if (createGameDto.betAmount() <= 0) {
            return Mono.error(new EmptyFieldException("Bet amount must be a positive number."));
        }

        return FIND_PLAYER_BY_ID_SERVICE.execute(createGameDto.playerId())
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + createGameDto.playerId())))
                .flatMap(player -> {
                    if (player.getBalance() < createGameDto.betAmount()) {
                        return Mono.error(new EmptyFieldException("Insufficient balance for the bet."));
                    }
                    return Mono.just(player);
                });
    }
}
