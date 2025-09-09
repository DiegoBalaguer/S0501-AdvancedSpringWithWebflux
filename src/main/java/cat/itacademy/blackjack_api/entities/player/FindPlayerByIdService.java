package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindPlayerByIdService {

    private final PlayerRepository playerRepository;

    public Mono<Player> execute(Long id) {
        return getFindPlayerById(id);
    }

    private Mono<Player> getFindPlayerById(Long id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)));
    }
}