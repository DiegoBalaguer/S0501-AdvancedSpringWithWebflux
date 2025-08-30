package cat.itacademy.blackjack_api.entities.game;

public record CreateGameDto(Long playerId, Long betAmount, int numberDecks) {}
