package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindGameByIdService {

    private final GameRepository gameRepository;

    public Mono<Game> execute(String id) {
        return getFindGameById(id);
    }

    private Mono<Game> getFindGameById(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Game not found with id: " + id)));
    }
}