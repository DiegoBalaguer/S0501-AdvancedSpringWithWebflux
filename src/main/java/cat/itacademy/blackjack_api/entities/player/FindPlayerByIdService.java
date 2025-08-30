package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindPlayerByIdService {

    private final PlayerRepository PLAYER_REPOSITORY;

    public FindPlayerByIdService(PlayerRepository playerRepository) {
        this.PLAYER_REPOSITORY = playerRepository;
    }

    public Mono<Player> execute(Long id) {
        return getFindPlayerById(id);
    }

    private Mono<Player> getFindPlayerById(Long id) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)));
    }
}