package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FindAllGameService {

    private final GameRepository GAME_REPOSITORY;

    public FindAllGameService(GameRepository gameRepository) {
        this.GAME_REPOSITORY = gameRepository;
    }

    public Flux<Game> execute() {
        return getFindAllGame();
    }

    private Flux<Game> getFindAllGame() {
        return GAME_REPOSITORY.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("Games not found")));
    }
}
