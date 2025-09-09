package cat.itacademy.blackjack_api.entities.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FindAllPlayersService {

    private final PlayerRepository playerRepository;

    public Flux<Player> execute() {
        return getFindAllPlayers();
    }

    private Flux<Player> getFindAllPlayers() {
        return playerRepository.findAll();
    }
}
