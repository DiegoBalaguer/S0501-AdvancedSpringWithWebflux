package cat.itacademy.blackjack_api.entities.player;


public record CreatePlayerDto (String name, String email, Long balance) {}