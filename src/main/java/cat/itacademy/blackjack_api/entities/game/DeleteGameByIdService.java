package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteGameByIdService {

    private final GameRepository gameRepository;

    public Mono<Void> execute(String id) {
        return deleteGameById(id);
    }

    public Mono<Void> deleteGameById(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + id)))
                .flatMap(existingGame -> gameRepository.deleteById(id));
    }
}
