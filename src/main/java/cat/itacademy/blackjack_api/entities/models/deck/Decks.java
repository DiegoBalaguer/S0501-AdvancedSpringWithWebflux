package cat.itacademy.blackjack_api.entities.models.deck;

import cat.itacademy.blackjack_api.config.properties.AppProperties;
import cat.itacademy.blackjack_api.entities.models.card.Card;
import cat.itacademy.blackjack_api.entities.models.card.Rank;
import cat.itacademy.blackjack_api.entities.models.card.Suit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Decks {

    private AppProperties appProperties;
    private int numberDecks;
    private List<Card> cards;

    public Decks(int numberDecks) {
        appProperties = new AppProperties();
        this.numberDecks = (numberDecks <= 0) ? appProperties.getDefaultDecks() : numberDecks;
        this.cards = new LinkedList<>();
        initializeDecks();
    }

    public void initializeDecks() {
        createDecks();
        shuffleDecks();
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty. Cannot deal a card.");
        }
        return cards.removeFirst();
    }

    private void createDecks() {
        cards.clear();
        for (int i = 0; i < numberDecks; i++) {
            createSingleDeck();
        }
    }

    private void createSingleDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    private void shuffleDecks() {
        Collections.shuffle(cards);
    }
}
