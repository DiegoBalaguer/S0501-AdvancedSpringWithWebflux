package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindGameByIdService {

    private final GameRepository GAME_REPOSITORY;

    public FindGameByIdService(GameRepository gameRepository) {
        this.GAME_REPOSITORY = gameRepository;
    }

    public Mono<Game> execute(String id) {
        return getFindGameById(id);
    }

    private Mono<Game> getFindGameById(String id) {
        return GAME_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + id)));
    }
}