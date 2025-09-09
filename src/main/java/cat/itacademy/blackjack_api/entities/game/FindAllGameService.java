package cat.itacademy.blackjack_api.entities.game;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FindAllGameService {

    private final GameRepository gameRepository;

    public Flux<Game> execute() {
        return getFindAllGame();
    }

    private Flux<Game> getFindAllGame() {
        return gameRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("Games not found")));
    }
}
