package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.entities.logic.BlackjackService;
import cat.itacademy.blackjack_api.entities.player.UpdatePlayerByIdService;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateGameService {

    private final GameRepository gameRepository;
    private final BlackjackService blackjackService;
    private final UpdatePlayerByIdService updatePlayerByIdService;

        public Mono<Game> execute(String gameId, GameActionDto actionDto) {
            return gameRepository.findById(gameId)
                    .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + gameId)))
                    .flatMap(game -> {
                        if (game.getStatus() != GameStatus.PLAYER_GAME) {
                            return Mono.just(game);
                        }
                        if (actionDto.action().equals(GameAction.HIT)) {
                            return blackjackService.playerHits(game)
                                    .flatMap(updatedGame -> {
                                        if (updatedGame.getPlayerHand().isBusted()) {
                                            updatedGame.setStatus(GameStatus.DEALER_WIN);
                                            return processGameEnd(updatedGame);
                                        }
                                        return gameRepository.save(updatedGame);
                                    });
                        } else if (actionDto.action().equals(GameAction.STAND)) {
                            return blackjackService.playerStands(game)
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
                game = blackjackService.dealerPlays(game);
            }

            game.setStatus(blackjackService.determineWinner(game));

            return gameRepository.save(game)
                    .flatMap(this::updatePlayerStats);
        }

    private Mono<Game> updatePlayerStats(Game game) {
        if (game.getStatus() == GameStatus.PLAYER_WIN) {
            return updatePlayerByIdService.addPlayerBalance(game.getPlayerId(), game.getBetAmount() * 2L)
                    .then(updatePlayerByIdService.addPlayerRankingScore(game.getPlayerId(), 1))
                    .thenReturn(game);
        } else if (game.getStatus() == GameStatus.DEALER_WIN) {
            return updatePlayerByIdService.addPlayerBalance(game.getPlayerId(), 0L)
                    .then(updatePlayerByIdService.addPlayerRankingScore(game.getPlayerId(), 0))
                    .thenReturn(game);
        } else if (game.getStatus() == GameStatus.DRAW) {
            return updatePlayerByIdService.addPlayerBalance(game.getPlayerId(), game.getBetAmount())
                    .then(updatePlayerByIdService.addPlayerRankingScore(game.getPlayerId(), 0))
                    .thenReturn(game);
        }
        return Mono.just(game);
    }
}
