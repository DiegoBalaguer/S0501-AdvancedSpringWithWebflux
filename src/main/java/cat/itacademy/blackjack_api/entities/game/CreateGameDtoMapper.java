package cat.itacademy.blackjack_api.entities.game;

public class CreateGameDtoMapper {

    public Game execute(CreateGameDto createGameDto) {
        return Game.builder()
                .playerId(createGameDto.playerId())
                .betAmount(createGameDto.betAmount())
                .numberDecks(createGameDto.numberDecks())
                .build();
    }
}
