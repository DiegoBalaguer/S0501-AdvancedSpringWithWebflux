package cat.itacademy.blackjack_api.entities.models;

import cat.itacademy.blackjack_api.entities.models.card.Card;
import cat.itacademy.blackjack_api.entities.models.card.Rank;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private boolean stood;

    public Hand(){
        this.cards = new ArrayList<>();
        this.stood = false;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && calculateValue() == 21;
    }

    public boolean isBusted(){
        return calculateValue() > 21;
    }

    public boolean hasTwentyOne() {
        return calculateValue() == 21;
    }

    public int calculateValue(){
        int valueCards = 0;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.rank() == Rank.ACE){
                aceCount++;
            }
            valueCards += card.rank().getValue();
        }
        valueCards += ((aceCount > 0) && (valueCards + 11 <= 21)) ? 11 : 0;
        return valueCards;
    }

    public void stand(){
        this.stood = true;
    }

    public boolean isStood(){
        return stood;
    }

    public boolean canHit(){
        return !stood && !isBusted() && !isBlackjack();
    }
}
