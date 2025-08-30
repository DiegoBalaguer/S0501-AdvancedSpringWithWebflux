package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.entities.logic.BlackjackService;
import cat.itacademy.blackjack_api.entities.player.UpdatePlayerByIdService;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateGameService {

    private final GameRepository GAME_REPOSITORY;
    private final BlackjackService BLACKJACK_SERVICE;
    private final UpdatePlayerByIdService UPDATE_PLAYER_BY_ID_SERVICE;

    public UpdateGameService(GameRepository gameRepository, BlackjackService blackjackService, UpdatePlayerByIdService updatePlayerByIdService) {
        this.GAME_REPOSITORY = gameRepository;
        this.BLACKJACK_SERVICE = blackjackService;
        this.UPDATE_PLAYER_BY_ID_SERVICE = updatePlayerByIdService;
    }

        public Mono<Game> execute(String gameId, GameActionDto actionDto) {
            return GAME_REPOSITORY.findById(gameId)
                    .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + gameId)))
                    .flatMap(game -> {
                        if (game.getStatus() != GameStatus.PLAYER_GAME) {
                            return Mono.just(game);
                        }
                        if (actionDto.action().equals(GameAction.HIT)) {
                            return BLACKJACK_SERVICE.playerHits(game)
                                    .flatMap(updatedGame -> {
                                        if (updatedGame.getPlayerHand().isBusted()) {
                                            updatedGame.setStatus(GameStatus.DEALER_WIN);
                                            return processGameEnd(updatedGame);
                                        }
                                        return GAME_REPOSITORY.save(updatedGame);
                                    });
                        } else if (actionDto.action().equals(GameAction.STAND)) {
                            return BLACKJACK_SERVICE.playerStands(game)
                                    .flatMap(updatedGame -> {
                                        updatedGame.setStatus(GameStatus.DEALER_GAME);
                                        return processGameEnd(updatedGame);
                                    });
                        }
                        return Mono.just(game);
                    });
        }

        private Mono<Game> processGameEnd(Game game) {
            if (game.getStatus() == GameStatus.DEALER_GAME) {
                game = BLACKJACK_SERVICE.dealerPlays(game);
            }

            game.setStatus(BLACKJACK_SERVICE.determineWinner(game));

            return GAME_REPOSITORY.save(game)
                    .flatMap(this::updatePlayerStats);
        }

    private Mono<Game> updatePlayerStats(Game game) {
        if (game.getStatus() == GameStatus.PLAYER_WIN) {
            return UPDATE_PLAYER_BY_ID_SERVICE.addPlayerBalance(game.getPlayerId(), game.getBetAmount() * 2L)
                    .then(UPDATE_PLAYER_BY_ID_SERVICE.addPlayerRankingScore(game.getPlayerId(), 1))
                    .thenReturn(game);
        } else if (game.getStatus() == GameStatus.DEALER_WIN) {
            return UPDATE_PLAYER_BY_ID_SERVICE.addPlayerBalance(game.getPlayerId(), 0L)
                    .then(UPDATE_PLAYER_BY_ID_SERVICE.addPlayerRankingScore(game.getPlayerId(), 0))
                    .thenReturn(game);
        } else if (game.getStatus() == GameStatus.DRAW) {
            return UPDATE_PLAYER_BY_ID_SERVICE.addPlayerBalance(game.getPlayerId(), game.getBetAmount())
                    .then(UPDATE_PLAYER_BY_ID_SERVICE.addPlayerRankingScore(game.getPlayerId(), 0))
                    .thenReturn(game);
        }
        return Mono.just(game);
    }
}
