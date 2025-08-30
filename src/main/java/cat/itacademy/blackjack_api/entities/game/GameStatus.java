package cat.itacademy.blackjack_api.entities.game;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Possible statuses of a Blackjack game.")

public enum GameStatus {
    @Schema(description = "The game is currently in progress, game player.")
    PLAYER_GAME,
    @Schema(description = "The game is currently in progress, game dealer.")
    DEALER_GAME,
    @Schema(description = "The player has won the game.")
    PLAYER_WIN,
    @Schema(description = "The dealer has won the game.")
    DEALER_WIN,
    @Schema(description = "The game ended in a draw between player and dealer.")
    DRAW
}
