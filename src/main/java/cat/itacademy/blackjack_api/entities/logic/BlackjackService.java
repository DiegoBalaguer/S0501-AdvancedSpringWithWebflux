package cat.itacademy.blackjack_api.entities.logic;

import cat.itacademy.blackjack_api.config.properties.AppProperties;
import cat.itacademy.blackjack_api.entities.game.Game;
import cat.itacademy.blackjack_api.entities.game.GameStatus;
import cat.itacademy.blackjack_api.entities.models.deck.Decks;
import cat.itacademy.blackjack_api.entities.models.deck.DecksProvider;
import cat.itacademy.blackjack_api.entities.models.Hand;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BlackjackService {

    private final DecksProvider DECKS_PROVIDER;
    private final AppProperties APP_PROPERTIES;

    public BlackjackService(DecksProvider decksProvider, AppProperties appProperties) {
        this.DECKS_PROVIDER = decksProvider;
        this.APP_PROPERTIES = appProperties;
    }

    public Mono<Game> startNewGame(Long playerId, Long bet, int numberOfDecks) {
        Game game = new Game();
        game.setPlayerId(playerId);
        game.setBetAmount(bet);
        game.setStatus(GameStatus.PLAYER_GAME);


        Decks decks = DECKS_PROVIDER.get(numberOfDecks <= 1 ? APP_PROPERTIES.getDefaultDecks() :  numberOfDecks);
        game.setDecks(decks);

        Hand playerHand = new Hand();
        playerHand.addCard(decks.dealCard());
        playerHand.addCard(decks.dealCard());
        game.setPlayerHand(playerHand);

        Hand dealerHand = new Hand();
        dealerHand.addCard(decks.dealCard());
        dealerHand.addCard(decks.dealCard());
        game.setDealerHand(dealerHand);

        return Mono.just(game);
    }

    public Mono<Game> playerHits(Game game) {
        if (game.getStatus() == GameStatus.PLAYER_GAME) {
            game.getPlayerHand().addCard(game.getDecks().dealCard());
        }
        return Mono.just(game);
    }

    public Mono<Game> playerStands(Game game) {
        game.setStatus(GameStatus.DEALER_GAME);
        return Mono.just(game);
    }

    public Game dealerPlays(Game game) {
        while (game.getDealerHand().calculateValue() < 17) {
            game.getDealerHand().addCard(game.getDecks().dealCard());
        }
        return game;
    }

    public GameStatus determineWinner(Game game) {
        int playerValue = game.getPlayerHand().calculateValue();
        int dealerValue = game.getDealerHand().calculateValue();

        if (playerValue > 21) {
            return GameStatus.DEALER_WIN;
        }
        if (dealerValue > 21) {
            return GameStatus.PLAYER_WIN;
        }
        if (playerValue > dealerValue) {
            return GameStatus.PLAYER_WIN;
        }
        if (dealerValue > playerValue) {
            return GameStatus.DEALER_WIN;
        }
        return GameStatus.DRAW;
    }
}

