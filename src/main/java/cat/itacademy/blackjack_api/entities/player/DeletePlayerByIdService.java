package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeletePlayerByIdService {

    private final PlayerRepository playerRepository;

    public Mono<Void> execute(Long id) {
        return deletePlayerById(id);
    }

    public Mono<Void> deletePlayerById(Long id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(existingPlayer -> playerRepository.deleteById(id));
    }
}
