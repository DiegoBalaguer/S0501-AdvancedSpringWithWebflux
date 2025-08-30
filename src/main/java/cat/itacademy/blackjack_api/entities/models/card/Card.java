package cat.itacademy.blackjack_api.entities.models.card;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a playing card in the Blackjack game.")

public record Card(Suit suit, Rank rank) {
}
