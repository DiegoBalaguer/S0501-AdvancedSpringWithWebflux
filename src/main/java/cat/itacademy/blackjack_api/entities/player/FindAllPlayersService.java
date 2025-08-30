package cat.itacademy.blackjack_api.entities.player;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FindAllPlayersService {

    private final PlayerRepository PLAYER_REPOSITORY;

    public FindAllPlayersService(PlayerRepository playerRepository) {
        this.PLAYER_REPOSITORY = playerRepository;
    }

    public Flux<Player> execute() {
        return getFindAllPlayers();
    }

    private Flux<Player> getFindAllPlayers() {
        return PLAYER_REPOSITORY.findAll();
    }
}
