package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteGameByIdService {

    private final GameRepository GAME_REPOSITORY;

    public DeleteGameByIdService(GameRepository gameRepository) {
        this.GAME_REPOSITORY = gameRepository;
    }

    public Mono<Void> execute(String id) {
        return deleteGameById(id);
    }

    public Mono<Void> deleteGameById(String id) {
        return GAME_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + id)))
                .flatMap(existingGame -> GAME_REPOSITORY.deleteById(id));
    }
}
