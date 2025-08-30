package cat.itacademy.blackjack_api.entities.models.deck;

import org.springframework.stereotype.Component;

@Component
public class SimpleDecksProvider implements DecksProvider {

    @Override
    public Decks get(int numberOfDecks) {
        return new Decks(numberOfDecks);
    }
}
