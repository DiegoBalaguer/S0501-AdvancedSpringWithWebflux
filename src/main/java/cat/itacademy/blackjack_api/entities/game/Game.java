package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.entities.models.deck.Decks;
import cat.itacademy.blackjack_api.entities.models.Hand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Schema(description = "Full internal model representing a Blackjack game stored in MongoDB.")
@Document(collection = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Game {

    @Schema(description = "Unique identifier of the game.", example = "64b7f13d6d7e8a32f9a12c45")
    @Id
    private String id;
    @Schema(description = "Identifier of the player linked to this game.", example = "1001")
    private Long playerId;
    @Schema(description = "Bet amount by player.")
    private Long betAmount;
    @Schema(description = "Number of decks.")
    private int numberDecks;
    @Schema(description = "Decks with list of cards currently for game.")
    @JsonIgnore
    private Decks decks;
    @Schema(description = "Hand with list of cards currently held by the player.")
    private Hand playerHand;
    @Schema(description = "Hand with list of cards currently held by the dealer.")
    private Hand dealerHand;
    @Schema(description = "Current status of the game.", implementation = GameStatus.class)
    private GameStatus status;
    @Schema(description = "Date when the game was created.", example = "2025-08-08")
    private LocalDateTime createdAt;
}
