package cat.itacademy.blackjack_api.entities.player;

public record UpdatePlayerDto (String name, Long balance, Integer rankingScore, String email) {}
