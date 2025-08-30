package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePlayerByIdService {

    private final PlayerRepository PLAYER_REPOSITORY;

    public DeletePlayerByIdService(PlayerRepository playerRepository) {
        this.PLAYER_REPOSITORY = playerRepository;
    }

    public Mono<Void> execute(Long id) {
        return deletePlayerById(id);
    }

    public Mono<Void> deletePlayerById(Long id) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(existingPlayer -> PLAYER_REPOSITORY.deleteById(id));
    }
}
