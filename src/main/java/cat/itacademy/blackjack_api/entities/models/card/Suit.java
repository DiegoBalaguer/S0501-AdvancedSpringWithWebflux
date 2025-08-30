package cat.itacademy.blackjack_api.entities.models.card;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Suit of the card.", example = "HEARTS")

public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;
}

