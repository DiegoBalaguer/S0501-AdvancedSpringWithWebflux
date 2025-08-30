package cat.itacademy.blackjack_api;

import cat.itacademy.blackjack_api.entities.game.Game;
import cat.itacademy.blackjack_api.entities.game.GameStatus;
import cat.itacademy.blackjack_api.entities.logic.BlackjackService;
import cat.itacademy.blackjack_api.entities.models.Hand;
import cat.itacademy.blackjack_api.entities.models.card.Card;
import cat.itacademy.blackjack_api.entities.models.card.Rank;
import cat.itacademy.blackjack_api.entities.models.card.Suit;
import cat.itacademy.blackjack_api.entities.models.deck.Decks;
import cat.itacademy.blackjack_api.entities.models.deck.DecksProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlackjackServiceTest {

    @Mock
    private DecksProvider mockedDecksProvider;

    @Mock
    private Decks mockedDecks;

    @InjectMocks
    private BlackjackService blackjackService;

    @Test
    public void startNewGame_shouldStartGameWithCorrectHands() {
        // Given: El proveedor devolverá un objeto mocked de Decks.
        when(mockedDecksProvider.get(2)).thenReturn(mockedDecks);

        // Y: El mazo mocked repartirá cartas específicas para el jugador y el crupier.
        when(mockedDecks.dealCard())
                .thenReturn(new Card(Suit.HEARTS, Rank.ACE)) // Primera carta del jugador
                .thenReturn(new Card(Suit.SPADES, Rank.KING)) // Segunda carta del jugador
                .thenReturn(new Card(Suit.CLUBS, Rank.QUEEN))  // Primera carta del crupier
                .thenReturn(new Card(Suit.DIAMONDS, Rank.JACK)); // Segunda carta del crupier

        // When: Se inicia un nuevo juego.
        Mono<Game> gameMono = blackjackService.startNewGame(1L, 50L, 2);

        // Then: El juego debería tener una mano de jugador y una de crupier con 2 cartas cada una.
        StepVerifier.create(gameMono)
                .assertNext(game -> {
                    assertEquals(2, game.getPlayerHand().getCards().size());
                    assertEquals(2, game.getDealerHand().getCards().size());
                })
                .verifyComplete();

        // Verify: Se verifican las llamadas a los mocks.
        verify(mockedDecks, times(4)).dealCard();
        verify(mockedDecksProvider).get(2);
    }

    @Test
    public void playerHits_shouldAddCardToPlayerHand() {
        // Given: Un juego en estado PLAYER_GAME.
        Game game = new Game();
        game.setStatus(GameStatus.PLAYER_GAME);
        game.setPlayerHand(new Hand());
        game.setDecks(mockedDecks);

        // Y: El mazo mocked repartirá una carta específica.
        when(mockedDecks.dealCard()).thenReturn(new Card(Suit.SPADES, Rank.TWO));

        // When: El jugador pide carta.
        Mono<Game> gameMono = blackjackService.playerHits(game);

        // Then: Se debería añadir una carta a la mano del jugador.
        StepVerifier.create(gameMono)
                .assertNext(updatedGame -> assertEquals(1, updatedGame.getPlayerHand().getCards().size()))
                .verifyComplete();
    }

    @Test
    public void playerStands_shouldChangeGameStatusToDealerGame() {
        // Given: Un juego en estado PLAYER_GAME.
        Game game = new Game();
        game.setStatus(GameStatus.PLAYER_GAME);
        game.setPlayerHand(new Hand());
        game.setDecks(mockedDecks);

        // When: El jugador se planta.
        Mono<Game> gameMono = blackjackService.playerStands(game);

        // Then: El estado del juego debería cambiar a DEALER_GAME.
        StepVerifier.create(gameMono)
                .assertNext(updatedGame -> assertEquals(GameStatus.DEALER_GAME, updatedGame.getStatus()))
                .verifyComplete();
    }
}